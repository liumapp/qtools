# 基于base64对图片进行旋转操作

> 现在是一个api满天飞的时代，各种api之间的请求数据形式也是json跟各种base64，那么这种情况下，如果我们想要修改接口数据中的base64图片该如何操作？先存到本地服务器再傻傻的读取吗？

答案肯定是不需要的。

首先，我们假设已经拿到了一张图片的base64值，并存放在一个名为base64Pic对象中。

````java
String base64Pic;
````

图片长这个样子：

![me.jpg](https://github.com/liumapp/qtools/blob/master/data/me.jpg?raw=true)

现在我们要做的事情就是把它逆时针旋转90度，变成这样：

![result.jpg](https://github.com/liumapp/qtools/blob/master/qtools-pic/docs/result.jpg?raw=true)

首先需要解析图片的Base64值，把它变成awt包下的一个BufferedImage实例。

因为BufferedImage可以通过一个字节输入流来创建，所以我们首先要解码图片的base64。

当然，常见的解码方式除了jdk自带的Base64之外，也可以使用sun公司提供的。

完成解码后，将获取图片的字节数组，通过一个字节输入流来读取它，并再通过ImageIO来创建最终的BufferedImage

````java
public static BufferedImage readBase64Image (String base64Pic) throws IOException {
    BufferedImage image = null;
    byte[] imageByte;
    ByteArrayInputStream bis;
    imageByte = Base64.getDecoder().decode(base64Pic);

    bis = new ByteArrayInputStream(imageByte);
    image = ImageIO.read(bis);
    bis.close();

    return image;
}
````

之后，便是旋转操作。



````java
public static BufferedImage rotateImage(BufferedImage pic1, double angle) throws IOException {
    int width = pic1.getWidth(null);
    int height = pic1.getHeight(null);

    angle = Math.toRadians(angle);
    double sin = Math.sin(angle);
    double cos = Math.cos(angle);
    double x0 = 0.5 * (width - 1);     // point to rotate about
    double y0 = 0.5 * (height - 1);     // center of image

    WritableRaster inRaster = pic1.getRaster();
    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    WritableRaster outRaster = pic2.getRaster();
    int[] pixel = new int[3];

    // rotation
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            double a = x - x0;
            double b = y - y0;
            int xx = (int) (+a * cos - b * sin + x0);
            int yy = (int) (+a * sin + b * cos + y0);

            if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
            }
        }
    }
    return pic2;
}
````










