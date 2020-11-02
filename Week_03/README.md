### 第五课笔记

![class05](./images/5.Netty_Gateway.png)

### 第六课笔记

![class6](./images/6.Java_Cocurrent.png)

### 作业

### 1、按今天的课程要求，实现一个网关，

基础代码可以 fork：https://github.com/kimmking/JavaCourseCodes

02nio/nio02 文件夹下

实现以后，代码提交到 Github

https://github.com/ibugs/JAVA-000/blob/main/Week_03/nio02/m/ma/ko/io/github/kimmking/gateway/NettyServerApplication.java



##### 1) 周四作业：整合你上次作业的httpclient/oktttp;

[HttpOutboundHandler](https://github.com/ibugs/JAVA-000/blob/main/Week_03/nio02/m/ma/ko/io/github/kimmking/gateway/outbound/httpclient4/HttpOutboundHandler.java)

##### 2) 周四作业（可选）：使用netty实现后端http访问（替代上一步）

##### 3）周六作业：实现过滤器

[HttpRequestFilterHandler](https://github.com/ibugs/JAVA-000/blob/main/Week_03/nio02/m/ma/ko/io/github/kimmking/gateway/filter/HttpRequestFilterHandler.java)

给传入的请求header里添加字段，后面根据headers里面的信息来实现过滤

``` Java
public class HttpRequestFilterHandler implements HttpRequestFilter {
    public final static String NIO_KEY = "NIO";

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if(null != fullRequest && null!= fullRequest.headers()){
            HttpHeaders httpHeaders = fullRequest.headers();
            httpHeaders.set(NIO_KEY, "VIP 111");
        }
    }
}
```



##### 4）周六作业（可选）：实现路由

[HttpEndpointRouterHandler](https://github.com/ibugs/JAVA-000/blob/main/Week_03/nio02/m/ma/ko/io/github/kimmking/gateway/router/HttpEndpointRouterHandler.java)

实现简单的路由，只要url里面包含hello，那么就全部跳转到`/api/hello`这个链接里面去。

其余的全部返回到`/nofund`这个无效链接上去。

``` 
public class HttpEndpointRouterHandler implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        if(endpoints.size() > 0 && endpoints.contains("hello")){
           return "/api/hello";
        }
        return "/nofund";
    }
}
```





