# qtools-http

## 1. UrlDownloadTool

使用URL进行文件的下载工具类

### 1.1 使用JavaIO

就下载文件而言，一般最常使用的就是Java IO。直接用URL类就可以跟网络资源建立连接再下载，并通过openStream()方法来获得一个输入流。

    BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
    
上面的代码，我用到了BufferedInputStream，通过缓存的形式来提升性能：

> 每一次用read()方法读取一个字节时，都会调用一次底层文件系统，所以每当JVM调用read()的时候，程序执行上下文都会从用户模式切换到内核模式，执行结束后再切换回来。

从性能角度来看，这种上下文切换的成本是高昂的：比如我们在读取一个字节数很高的文件时，大量的上下文切换将会很影响程序性能。

所以这里我们最好使用BufferedInputStream来规避这种情况（具体原理请见下文）

而要把读取到的URL文件字节写入到本地文件，一般直接用FileOutputSream类的write()方法就可以了：

    try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
      FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
    } catch (IOException e) {
        // handle exception
    }
    
在使用BufferedInputStream的时候，read()方法会根据我们设置的buffer size一次性读取等量的字节（不设置的话，jkd1.8里是默认8192个字节）

上面的示例代码里，dataBuffer已经规定了一次性读取1024个字节，所以第二次读取的时候就不需要再使用BufferedInputStream了

上面那个示范代码其实是针对jdk1.6等版本的，jkd1.7以后，实现同样的功能不需要这么啰嗦了

一个Files.copy()方法就可以搞定    

    InputStream in = new URL(FILE_URL).openStream();
    Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
    
使用Java IO实现网络资源的下载就是这么简单，不过它也有缺点：所有的缓存字节都会直接存储在内存中

而使用NIO的话，我们就不需要用到缓存，而是直接从两个通道进行字节的流动    

### 1.2 使用 NIO

The Java NIO package offers the possibility to transfer bytes between 2 Channels without buffering them into the application memory.

To read the file from our URL, we’ll create a new ReadableByteChannel from the URL stream:



    ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
    
The bytes read from the ReadableByteChannel will be transferred to a FileChannel corresponding to the file that will be downloaded:

    FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
    FileChannel fileChannel = fileOutputStream.getChannel();
    
We’ll use the transferFrom() method from the ReadableByteChannel class to download the bytes from the given URL to our FileChannel:

    fileOutputStream.getChannel()
        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        
The transferTo() and transferFrom() methods are more efficient than simply reading from a stream using a buffer. 
Depending on the underlying operating system, the data can be transferred directly from the filesystem cache to our file without copying any bytes into the application memory.

On Linux and UNIX systems, these methods use the zero-copy technique that reduces the number of context switches between the kernel mode and user mode.                

### 1.3 Using Libraries

We’ve seen in the examples above how we can download content from a URL just by using the Java core functionality. 
We also can leverage the functionality of existing libraries to ease our work, when performance tweaks aren’t needed.

For example, in a real-world scenario, we’d need our download code to be asynchronous.

We could wrap all the logic into a Callable, or we could use an existing library for this.

#### 1.3.1 Async HTTP Client

AsyncHttpClient is a popular library for executing asynchronous HTTP requests using the Netty framework. 
We can use it to execute a GET request to the file URL and get the file content.

First, we need to create an HTTP client:

    AsyncHttpClient client = Dsl.asyncHttpClient();
    
The downloaded content will be placed into a FileOutputStream:

    FileOutputStream stream = new FileOutputStream(FILE_NAME);
    
Next, we create an HTTP GET request and register an AsyncCompletionHandler handler to process the downloaded content:

    client.prepareGet(FILE_URL).execute(new AsyncCompletionHandler<FileOutputStream>() {
     
        @Override
        public State onBodyPartReceived(HttpResponseBodyPart bodyPart) 
          throws Exception {
            stream.getChannel().write(bodyPart.getBodyByteBuffer());
            return State.CONTINUE;
        }
     
        @Override
        public FileOutputStream onCompleted(Response response) 
          throws Exception {
            return stream;
        }
    })        

Notice that we’ve overridden the onBodyPartReceived() method. 
The default implementation accumulates the HTTP chunks received into an ArrayList. 
This could lead to high memory consumption, or an OutOfMemory exception when trying to download a large file.

Instead of accumulating each HttpResponseBodyPart into memory, we use a FileChannel to write the bytes to our local file directly. 
We’ll use the getBodyByteBuffer() method to access the body part content through a ByteBuffer.

ByteBuffers have the advantage that the memory is allocated outside of the JVM heap, so it doesn’t affect out applications memory.

#### 1.3.2 Apache Commons IO

Another highly used library for IO operation is Apache Commons IO. 
We can see from the Javadoc that there’s a utility class named FileUtils that is used for general file manipulation tasks.

To download a file from a URL, we can use this one-liner:

    FileUtils.copyURLToFile(
      new URL(FILE_URL), 
      new File(FILE_NAME), 
      CONNECT_TIMEOUT, 
      READ_TIMEOUT);
      
From a performance standpoint, this code is the same as the one we’ve exemplified in section 2.

The underlying code uses the same concepts of reading in a loop some bytes from an InputStream and writing them to an OutputStream.

One difference is the fact that here the URLConnection class is used to control the connection timeouts so that the download doesn’t block for a large amount of time:

    URLConnection connection = source.openConnection();
    connection.setConnectTimeout(connectionTimeout);
    connection.setReadTimeout(readTimeout);
             

#### 1.4 Resumable Download

Considering internet connections fail from time to time, it’s useful for us to be able to resume a download, instead of downloading the file again from byte zero.

Let’s rewrite the first example from earlier, to add this functionality.

The first thing we should know is that we can read the size of a file from a given URL without actually downloading it by using the HTTP HEAD method:

    URL url = new URL(FILE_URL);
    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
    httpConnection.setRequestMethod("HEAD");
    long removeFileSize = httpConnection.getContentLengthLong();
    
Now that we have the total content size of the file, we can check whether our file is partially downloaded. 
If so, we’ll resume the download from the last byte recorded on disk:

    long existingFileSize = outputFile.length();
    if (existingFileSize < fileLength) {
        httpFileConnection.setRequestProperty(
          "Range", 
          "bytes=" + existingFileSize + "-" + fileLength
        );
    }    

What happens here is that we’ve configured the URLConnection to request the file bytes in a specific range. 
The range will start from the last downloaded byte and will end at the byte corresponding to the size of the remote file.

Another common way to use the Range header is for downloading a file in chunks by setting different byte ranges. 
For example, to download 2 KB file, we can use the range 0 – 1024 and 1024 – 2048.

Another subtle difference from the code at section 2. 
is that the FileOutputStream is opened with the append parameter set to true:

    OutputStream os = new FileOutputStream(FILE_NAME, true);
    
After we’ve made this change the rest of the code is identical to the one we’ve seen in section 2.    


