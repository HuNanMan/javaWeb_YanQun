### JSP访问数据库

JSP就是在html中嵌套的java代码，因此 java代码可以写在jsp中（<%  ... %>）。

因此可以利用jsp把账号密码等信息传入，在check.jsp中嵌入java代码进行验证、读取数据库。

**导包操作：**

* java项目 ：1 Jar复制到工程中 2.右键该Jar :build path ->add to build Path
* Web项目：jar复制到WEB-INF/lib

核心：就是将 java中的JDBC代码，复制到 JSP中的<% ... %>

**注意**：如果jsp出现错误：The import Xxx cannot be resolved
尝试解决步骤：

* (可能是Jdk、tomcat版本问题) 右键项目->build path，将其中 报错的 libary或Lib 删除后 重新导入
* 清空各种缓存：右键项目->Clean tomcat... clean  （Project -clean或者 进tomcat目录 删除里面work的子目录）
* 删除之前的tomcat，重新解压缩、配置tomcat，重启计算机
* 如果类之前没有包，则将该类加入包中
  	

### JavaBean
刚才我们将 jsp中 登录操作的代码  转移到了LoginDao.java；其中LoginDao类 就称之为JavaBean。

**JavaBean的作用**：

* 减轻的jsp复杂度  
* 提高代码复用（以后任何地方的 登录操作，都可以通过调用LoginDao实现）

JavaBean（就是一个Java类）的定义：满足一下2点 ，就可以称为JavaBean

* public 修饰的类  ,public 无参构造
* 所有属性(如果有) 都是private，并且提供set/get   (如果boolean 则get 可以替换成is)

使用层面，Java分为2大类：

* 封装业务逻辑的JavaBean (LoginDao.java封装了登录逻辑)			逻辑
  	可以将jsp中的JDBC代码，封装到Login.java类中 （Login.java）
* 封装数据的JavaBean   （实体类，Student.java  Person.java  ）		数据 
  	对应于数据库中的一张表
  	Login login = new Login(uname,upwd) ;//即用Login对象 封装了2个数据（用户名 和密码）

封装数据的JavaBean 对应于数据库中的一张表   (Login(name,pwd))
封装业务逻辑的JavaBean 用于操作 一个封装数据的JavaBean  

可以发现，JavaBean可以简化 代码(jsp->jsp+java)、提供代码复用(LoginDao.java)