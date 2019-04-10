
###  response :响应对象
#### 提供的方法：
* void addCookie( Cookie cookie ); 	服务端向客户端增加cookie对象
* void sendRedirect(String location ) throws IOException;              页面跳转的一种方式（重定向）
* void setContetType(String type)          设置服务端响应的编码（设置服务端的contentType类型）

#### 示例：登陆例子
**流程**：login.jsp  -> check.jsp ->success.jsp

login.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="check.jsp" method="post">
		用户名:<input type="text" name="uname"><br/>
		密码:<input type="password" name="upwd"><br/>
		<input type="submit" value="登陆"><br/>
		
	</form>
</body>
</html>
```

check.jsp:
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%
			request.setCharacterEncoding("utf-8") ;
			String name = request.getParameter("uname");
			String pwd = request.getParameter("upwd");
			if(name.equals("zs") && pwd.equals("abc")){//假设 zs abc
				response.sendRedirect("success.jsp") ;//页面跳转：重定向， 导致数据丢失
				//页面跳转：请求转发, 可以获取到数据，并且 地址栏 没有改变（仍然保留 转发时的页面check.jsp）
				//request.getRequestDispatcher("success.jsp").forward(  request,response);
			}else{
				//登陆失败
				out.print("用户名或密码有误！") ;
			}
		
		%>
</body>
</html>
```

success.jsp:
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		登录成功！<br/>
		欢迎您：
		<%
			  String name = request.getParameter("uname") ;
				out.print(name) ;
 		%>
</body>
</html>
```





**注意**：

* 请求转发封装在request，
* 重定向封装在response里。

|                                      | 请求转发        | 重定向                 |
| ------------------------------------ | --------------- | ---------------------- |
| 地址栏是否改变                       | 不变(check.jsp) | 改变(success.jsp)      |
| 是否保留第一次请求时的数据的请求次数 | 保留            | 不保留                 |
| 请求时的                             | 1               | 2                      |
| 跳转发生的位置                       | 服务端          | 客户端发出的第二次跳转 |



#### 转发、重定向：

**转发**：  
	 张三（客户端）     ->    【 服务窗口 A （服务端 ）    ->  服务窗口B  】

**重定向**：
	张三（客户端） 	  -> 	服务窗口 A （服务端 ）  ->去找B

	    张三（客户端）      -> 	服务窗口 B （服务端 ） ->结束



### Cookie
Cookie（客户端，不是内置对象）:Cookie是由服务端生成的，再发送给客户端保存。相当于本地缓存的作用： 客户端(hello.mp4,zs/abc)->服务端(hello.mp4；zs/abc)
**作用**：提高访问服务端的效率，但是安全性较差。

Cookie保存的键值对 name=value  。	 

完整类名为：javax.servlet.http.Cookie

* public Cookie(String name,String value)
* String getName()：获取name
* String getValue():获取value
* void setMaxAge(int expiry);最大有效期 （秒）

##### Cookie流程：

* 服务端准备Cookie：
  	response.addCookie(Cookie cookie)
* 页面跳转（转发，重定向）
* 客户端获取cookie:  request.getCookies();

**注意**：

* 服务端增加cookie :response对象；客户端获取对象：request对象。
* 不能直接获取某一个单独对象，只能一次性将 全部的cookie拿到。

* 在浏览器中通过F12可以发现 ，除了自己设置的Cookie对象外，还有一个name为 JSESSIONID的cookie。

* 建议 cookie只保存  英文数字，否则需要进行编码、解码。



### Cookie实例：使用Cookie实现  记住用户名  功能

在第一次登录后，服务器会发送一个Cookie给客户端。客户端保存后，下次打开页面，就会直接显示value值。

* login.jsp: 客户端（浏览器）发送登录请求

* check,jsp: 服务器接受，并重定向（请求转发方式也行）至客户端，形成交互，从而把Cookie传给客户端。

* A.jsp：	位于客户端的页面。（这个页面的内容不重要，设置为空。）
* 如果name输入为中文，会报错：500。因为还需要编解码，这个留待以后考虑。
* name输入为数字和字母，不要中文。

##### login.jsp:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%!
		String uname  ;<!--存放全局变量-->
	%>
	<%
		boolean flag = false ;
		Cookie[] cookies = request.getCookies() ;
		for(Cookie cookie :cookies){<!--寻找包含uname的value值。-->
			if(cookie.getName().equals("uname")){
				uname = cookie.getValue() ;
				flag = true ;
			}
		}
		
		if(!flag){//if(flag ==true)
			out.print("cookie已失效！");
		}else{
			out.print("cookie:"+uname);
		}
	
	%>


	<form action="check.jsp" method="post">
		用户名:<input type="text" name="uname"  value="<%=(uname==null?"":uname)%>"><br/>
		密码:<input type="password" name="upwd"><br/>
		<input type="submit" value="登陆"><br/>
		
	</form>
</body>
</html>
```
##### check.jsp:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%
			request.setCharacterEncoding("utf-8") ;
			String name = request.getParameter("uname");
			String pwd = request.getParameter("upwd");
			
			//将用户名 加入到Cookie种
			Cookie cookie = new Cookie("uname",name);
			
			cookie.setMaxAge(10) ;
			
			response.addCookie(cookie) ;
			
			response.sendRedirect("A.jsp") ;
		
		%>
</body>
</html>
```
##### A.jsp:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>

	</body>
</html>
```