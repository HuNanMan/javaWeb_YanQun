#### tomcat的目录如下：

![1.1](https://github.com/HuNanMan/notes-javaWeb-YanQun/blob/master/pictures/1/1.1.png)

********

#### 1. 在apache-tomcat-xxxx\webapps\新建自己的目录，如demo01，

则demo01中也需要WEB-INFO文件夹，classes目录、lib目录。（手动创建即可）

* web.xml文件：　　　存放在WEB-INFO目录下。、
* classes目录:　　　　存放字节码文件。（创建个空的就行。）
* lib目录：　　　　　   存放运行时需要的一些jar包。（没有特殊需求，创建个空的就行）

**注意**：_apache-tomcat-xxxx目录下的lib目录存放的jar文件，tomcat下所有项目共享，而demo01中的lib目录中的jar，只能demo01使用。这是局部和整体的关系。_



#### 2. 在demo01目录下，创建index.jsp文件，默认会访问这个名字的文件。比如如下：

```jsp

<html>

　　	<head> 
　　　	　<title>ppp</title>
　	</head>
	<body>
　　	kkkkdd
　　		<%
　　　		　out.print("hello world!");
　　		%>
	</body>
</html>
```

#### 3. 开启tomcat，在浏览器中输入：  _localhost:8080/demo01_

得到如下结果：

![1.2](https://github.com/HuNanMan/notes-javaWeb-YanQun/blob/master/pictures/1/1.2.png)

##### 若在浏览器中输入：localhost:8080

​     **注意**：

* 在ie中直接这样输入会进入搜索引擎,必须完整输入：http://localhost:8080
* 在chrome，它会自动加上http://，可以进入tomcat首页，也就是默认进入apache-tomcat-xxx\webapps\ROOT，打开默认的index.jsp。



#### 如果想把默认文件从index.jsp修改为其他页面文件，可以如下设置：

* 修改对应项目的web.xml文件。如输入localhost:8080，则要修改ROOT目录下的WEB-INFO下的web.xml文件；其他项目修改它的项目目录下WEB-INFO目录下的web.xml即可。

* 修改方法为，在web.xml中增加如下内容：（按照自己的需求稍作修改即可）

				```xml
						<welcome-file-list>
							<welcome-file>index.html</welcome-file>
							<welcome-file>index.xhtml</welcome-file>
							<welcome-file>index.htm</welcome-file>
							<welcome-file>index.jsp</welcome-file>
						</welcome-file-list>
	```

* 这段代码的意思是，设置进入这个目录的默认初始页面，有index.html就打开，没有就往下找。



## 虚拟路径：

* 在浏览器中输入：http://localhost:8080，它实际打开的是服务器中 webapps\ROOT，也就是说进入服务器根目录时，自动转入webapps，因为没有指定项目名，所以默认访问ROOT项目。这是虚拟路径的例子。

* 假如我们输入：http://localhost:8080/demo01/，也就是说进入demo01项目，而demo01项目没在webapps目录下，我们可以利用虚拟路径技术，自动转向其他路径。

我们可以修改配置文件，访问在webapps以外的目录中的web项目。

 

**方法一**：（需要重启tomcat）

* conf/server.xml中配置

* host标签中:

```xml
<Host name="localhost" appBase="webapps"
　　unpackWARs="true" autoDeploy="true">

<!-- SingleSignOn valve, share authentication between web applications
Documentation at: /docs/config/valve.html -->
<!--
<Valve className="org.apache.catalina.authenticator.SingleSignOn" />
-->

<!-- Access log processes all example.
Documentation at: /docs/config/valve.html
Note: The pattern used is equivalent to using pattern="common" -->
<Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
prefix="localhost_access_log." suffix=".txt"
pattern="%h %l %u %t &quot;%r&quot; %s %b" />
<Context docBase="D:\tomcat\apache-tomcat-7.0.56\demo01" path="/demo01" /><!--插入这段代码.从/demo01转入D:\tomcat\apache-tomcat-7.0
```


**方法二**：（不需要重启tomcat）

apache-tomcat-xxx\conf\Catalina\localhost

中新建文件：   “项目名.xml”,

并新增一行：
```xml
<Context docBase="D:\tomcat\apache-tomcat-7.0.56\demo01" path="/demo01" />
```



### 虚拟主机：

**目标**：通过www.test.com访问D:\study\JspProject。

 

a. 修改conf/server.xml

把这句修改成这样:
```xml
	<Engine name="Catalina" defaultHost="www.test.com">
```

并增加下面这段：
```xml
	<Host appBase="D:\study\JspProject" name="www.test.com"><Context docBase="D:\study\JspProject" path="/"/></Host>*

```


b.C:\Windows\System32\drivers\etc\host增加下面这句：
		_127.0.0.1 www.test.com_

由于浏览器默认访问80端口，

*此时可以通过www.test.com:8080访问JspProject。*

 

 

c.继续修改conf/server.xml
```xml
<Connector port="80" protocol="HTTP/1.1"
　　connectionTimeout="20000"
　　redirectPort="8443" />
```

d:此时终于可以直接在浏览器输入www.test.com访问jspProject了。
