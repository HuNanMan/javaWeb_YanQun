### JDBC（Java DataBase Connectivity  ）

|||||||||||||插入图片

* 可以为多种关系型数据库DBMS 提供统一的访问方式，用Java来操作数据库。（是个模板，有固定流程。）
* jdbc相关的包都在java.sql中。

#####  jdbc访问数据库的具体步骤：

* 导入驱动，加载具体的驱动类
* 与数据库建立连接
* 发送sql，执行
* 处理结果集 （查询）



####  各个数据库驱动

|数据库类型    |驱动jar	|	具体驱动类	|  连接字符串  |
|-------------|---------|---------------|------------|
|Oracle		        |ojdbc-x.jar    |oracle.jdbc.OracleDriver|jdbc:oracle:thin:@localhost:1521:ORCL|
|MySQL		|mysql-connector-java-x.jar	|	                 com.mysql.jdbc.Driver	|		    	jdbc:mysql://localhost:3306/数据库实例名|
|SqlServer	     |            sqljdbc-x.jar		|		com.microsoft.sqlserver.jdbc.SQLServerDriver|		jdbc:microsoft:sqlserver:localhost:1433;databasename=数据库实例名|

**使用jdbc操作数据库时，如果对数据库进行了更换，只需要替换：驱动、具体驱动类、连接字符串、用户名、密码。（是个模板，有固定流程。）**

### 例子:(增删改查update()、query())

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCDemo {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String USERNAME = "scott";
	private static final String PWD = "tiger";

	public static void update() {// 增删改
		Connection connection = null;
		Statement stmt = null;
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("oracle.jdbc.OracleDriver");// 加载具体的驱动类
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			// c.发送sql，执行(增删改、查)
			stmt = connection.createStatement();
			String sql = "delete from student where stuno=1";
			// 执行SQL
			int count = stmt.executeUpdate(sql); // 返回值表示 增删改 几条数据
			// d.处理结果
			if (count > 0) {  
				System.out.println("操作成功！");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				 if(stmt!=null) stmt.close();// 对象.方法
				 if(connection!=null)connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
    
    public static void query() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null ; 
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("oracle.jdbc.OracleDriver");// 加载具体的驱动类
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			// c.发送sql，执行(增删改、【查】)
			stmt = connection.createStatement();
     		String sql = "select * from student";
			rs = stmt.executeQuery(sql); 
			// d.处理结果
			int count = -1;
			if(rs.next()) {
				count = rs.getInt(1) ;
			}
			if(count>0) {
				System.out.println("登陆成功！");
			}else {
				System.out.println("登陆失败！");
			}
			while(rs.next()){//循环输出结果
				//不推荐这种方式，直接用字段名比较好。即rs.getInt(stuno)
				int num1 = rs.getInt(1);
				String name1 =rs.getString(2);
				System.out.println(num1+" " + name1);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) rs.close(); //后开的需要先关
				 if(stmt!=null) stmt.close();// 对象.方法
				 if(connection!=null)connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		//update() ;
        //query() ;
	}
}
```

####  JDBC API 主要功能：
三件事，具体是通过以下类/接口实现：(从上往下产生)

* DriverManager ： 管理jdbc驱动
* Connection： 连接（通过DriverManager产生）
* Statement（PreparedStatement） ：增删改查  （通过Connection产生 ）
* CallableStatement  ： 调用数据库中的 存储过程/存储函数  （通过Connection产生 ）
* Result ：返回的结果集  （上面的Statement等产生 ）

##### Connection产生的操作数据库的对象：

* Connection产生Statement对象：createStatement()
* Connection产生PreparedStatement对象：prepareStatement()
* Connection产生CallableStatement对象：prepareCall();

