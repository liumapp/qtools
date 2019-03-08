# qtools-pic

## 功能



## 注意事项

* java-1.8/jre/lib/i386/libfontmanager.so: libgcc_s.so.1: 无法打开共享对象文件: 没有那个文件或目录

    需要安装lib32gcc1包：一般os安装的是64位的，所以缺省是装libgcc这个包。但java一般还是会用32位的包，因此就会存在上面的问题（系统提供的libgcc_s.so.1是64位的，不是java启动需要的32位的），安装一个32位的就好了
    
    有必要说明一下：如果你的服务器之前就装了libgcc.x86_64  的话，就会报一个
        
        Multilib version problems found. 
        This often means that the root cause is something else and multilib version checking is just pointing out that there is a problem. 

     这个是原因是因为多个库不能共存，不过更新的话也不行，但是可以在安装命令后面加上
    
            --setopt=protected_multilib=false
            
* 生成的公章上面的字全被换成了框框（线上服务器缺少字体文件）

    首先在线上服务器使用命令
    
        which java
        
    找到jre安装目录，假设为 /usr/local/java/jre/
    
    那么将要使用的中文字体文件（可以从本地系统查找到，一般使用宋体，英文名称为simsun.ttf）拷贝到 /usr/local/java/jre/lib/fonts/ 目录下即可
    
    
    