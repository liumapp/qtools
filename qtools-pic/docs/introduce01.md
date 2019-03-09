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











