### MVC设计模式：
* M：Model	，模型  ：一个功能。用JavaBean实现。
* V:View，视图： 用于展示、以及与用户交互。使用html  js  css jsp jquery等前端技术实现
* C:Controller，控制器 ：接受请求，将请求跳转到模型进行处理；模型处理完毕后，再将处理的结果
  			返回给 请求处 。 可以用jsp实现，  但是一般建议使用 Servlet实现控制器。
* Jsp->Java(Servlet)->JSP





###  Servlet基础：
Java类必须符合一定的 规范：
	a.必须继承  javax.servlet.http.HttpServlet
	b.重写其中的 doGet()或doPost()方法

 doGet()： 接受 并处 所有get提交方式的请求
 doPost()：接受 并处 所有post提交方式的请求


Servlet要想使用，必须配置
Serlvet2.5：web.xml
Servle3.0： @WebServlet


Serlvet2.5：web.xml:

##### 项目的根目录：WebContent 、src

<a href="WelcomeServlet">所在的jsp是在 WebContent目录中，因此 发出的请求WelcomeServlet  是去请求项目的根目录。

Servlet流程：
请求 -><url-pattern> -> 根据<servlet-mapping>中的<servlet-name> 去匹配  <servlet> 中的<servlet-name>，然后寻找到<servlet-class>，求中将请求交由该<servlet-class>执行。



2个/：
jsp:	/ 代表  localhost:8888
web.xml: 	/   代表
```
http://localhost:8888/项目名/
```



#### 回顾纯手工方法创建第一个Servlet
步骤：
编写一个类，继承HttpServlet
重写doGet()、doPost()方法
编写web.xml 中的servlet映射关系

2.借助于Eclipse快速生成Servlet
直接新建Servlet即可！（继承、重写、web.xml  可以借助Eclipse自动生成）

##### Servlet3.0，与Servlet2.5的区别：
Servlet3.0不需要在web.xml中配置，但 需要在 Servlet类的定义处之上编写 注解@WebServlet("url-pattern的值") 
匹配流程：  请求地址 与@WebServlet中的值 进行匹配，如果匹配成功 ，则说明 请求的就是该注解所对应的类

**项目根目录**：WebContent、src（所有的构建路径）
例如：
WebContent中有一个文件index.jsp
src中有一个Servlet.java  

* 如果: index.jsp中请求 <a href="abc">...</a> ，则 寻找范围：既会在src根目录中找  也会在WebContent根目录中找
* 如果：index.jsp中请求<a href="a/abc"></a>，寻找范围：先在src或WebContent中找a目录，然后再在a目录中找abc

web.xml中的 /:代表项目根路径
```
http://localhost:8888/Servlet25Project/
```
jsp中的/: 服务器根路径
```
http://localhost:8888/
```
**构建路径、WebContent:根目录**


### Servlet生命周期：5个阶段 
加载
初始化： init()  ，该方法会在 Servlet被加载并实例化的以后 执行
服务  ：service() ->doGet()  doPost
销毁  ：destroy()，  Servlet被系统回收时执行
卸载


init():
	a.默认第一次访问 Servlet时会被执行 （只执行这一次）
	b.可以修改为 Tomcat启动时自动执行
		i.Servlet2.5：  web.xml
			  <servlet>
				...
  				 <load-on-startup>1</load-on-startup>
    			</servlet>
			其中的“1”代表第一个。
		ii.Servlet3.0
			@WebServlet( value="/WelcomeServlet" ,loadOnStartup=1  )

service() ->doGet()  doPost ：调用几次，则执行几次
destroy()：关闭tomcat服务时，执行一次。