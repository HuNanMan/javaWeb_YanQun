## session :会话

* 浏览网站：开始-关闭
* 购物：  浏览、付款、退出
* 电子邮件：浏览、写邮件、退出
* 开始到结束的整个周期

##### 机制：

客户端第一次请求服务端时，（jsessionid-sessionid）服务端会产生一个session对象（用于保存该客户的信息）； 并且每个session对象 都会有一个唯一的 sessionId( 用于区分其他session);服务端由会 产生一个cookie，并且 该cookie的name=JSESSIONID ,value=服务端sessionId的值；然后 服务端会在 响应客户端的同时 将该cookie发送给客户端，至此 客户端就有了 一个cookie(JSESSIONID)；因此，客户端的cookie就可以和服务端的session一一对应（JSESSIONID - sessionID）

客户端**第二/n次**请求服务端时:服务端会先用客户端cookie种的JSESSIONID  去服务端的session中匹配sessionid,如果匹配成功（cookie  jsessionid和sesion sessionid），说明此用户 不是第一次访问,无需登录；

##### 例子
客户端：		    顾客（客户端）
服务端:			 存包处   -  商场(服务端)

顾客第一次存包：商场 判断此人是 之前已经存过包（通过你手里是否有钥匙）。
 如果是新顾客（没钥匙） ，分配一个钥匙 给该顾客； 钥匙 会和 柜子 一一对应；

 第二/n次 存包：商场 判断此人是 之前已经存过包（通过你手里是否有钥匙）
 如果是老顾客（有钥匙），则不需要分配；该顾客手里的钥匙 会 和柜子 自动一一对应。

##### session特点：

* session存储在服务端。
* session是在 同一个用户（客户）请求时 共享。
* 实现机制：第一次客户请求时 产生一个sessionid 并复制给 cookie的jsessionid 然后发给客户端。最终 通过session的sessionid-cookie的jsessionid。

session方法：

* String getId() :获取sessionId  
* boolean isNew() :判断是否是 新用户（第一次访问）
* void invalidate():使session失效  （退出登录、注销）
* void setAttribute()
* Object getAttribute();
* void setMaxInactiveInterval(秒) ：设置最大有效 非活动时间 
* int getMaxInactiveInterval():获取最大有效 非活动时间 

##### 示例：登录

客户端在第一次请求服务端时，如果服务端发现 此请求没有 JSESSIONID,则会创建一个 name=JSESIONID的cookie  并返回给客户端。

##### Cookie特点：
* 不是内置对象，要使用必须new。
* 但是，服务端会 自动生成一个(服务端自动new一个cookie) name=JSESIONID的cookie  并返回给客户端。

##### cookie和session的区别：
|                     | session      |cookie        |
| ---------------- | ---------------|----------------|
|保存的位置 | 服务端  |	客户端	|
|安全性     |较安全   |较不安全|
|保存的内容	|Object	      |	String	|



#####  appliation 全局对象

* String getContextPath()	虚拟路径
* String getRealPath(String name): 绝对路径（虚拟路径 相对的绝对路径）



#####  JSP9大内置对象

* pageContext  JSP页面容器（有些书中的page对象指的是这个，不是page）
* request   请求对象
* session   会话对象
* appliation 全局对象
* response  响应对象
* config  配置对象（服务器配置信息）
* out    输出对象
* page   当前JSP页面对象（相当于java中的this）
* exception 异常对象



#####  四种范围对象（小->大）

* pageContext  JSP页面容器   （page对象）； 当前页面有效
* request   请求对象		 	同一次请求有效
* session   会话对象			同一次会话有效
* appliation 全局对象			全局有效（整个项目有效）

######  以上4个对象共有的方法：

* Object getAttribute(String name):根据属性名，或者属性值
* void setAttribute(String name,Object obj) :设置属性值（新增，修改）
  	setAttribute("a","b") ;//如果a对象之前不存在，则新建一个a对象 ；
  				 如果a之前已经存在，则将a的值改为b
* void removeAttribute(String name)：根据属性名，删除对象



##### 四种范围对象的比较

* pageContext 当前页面有效 (页面跳转后无效，包括重定向和请求转发)

* request   同一次请求有效；其他请求无效 （请求转发后有效；重定向后无效，重定向有两次请求）
* session  同一次会话有效  （无论怎么跳转，都有效；关闭/切换浏览器后无效 ； 从 登陆->退出 之间 全部有效）
* application
  	全局变量；整个项目运行期间 都有效 (切换浏览器 仍然有效)；关闭服务、其他项目 无效

->多个项目共享、重启后仍然有效 ：JNDI可以实现

1.以上的4个范围对象，通过 setAttribute()复制，通过getAttribute()取值；
2.以上范围对象，尽量使用最小的范围。因为 对象的范围越大，造成的性能损耗越大。