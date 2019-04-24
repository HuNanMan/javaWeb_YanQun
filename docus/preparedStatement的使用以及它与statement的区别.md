#### preparedStatement例子：

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCDemo {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:MLDN";
	private static final String USERNAME = "c##scott";
	private static final String PWD = "tiger";

	public static void update() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			/*以上和statement一样----------------------------------------*/
			String sql = "insert into student values(?,?,?)";//在preparedStatement中，此处可以用占位符?表示。
			pstmt = connection.prepareStatement(sql);//注意此处和statement的区别，有参数sql。
			//以下三句，设置三个占位符的具体值。
			pstmt.setInt(1, 10);
			pstmt.setString(2, "cccq");
			pstmt.setInt(3, 50);
			int count = pstmt.executeUpdate();//此处不用像statement那样加sql参数。
			// d.处理结果
			if (count > 0){  
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
				 if(pstmt!=null) pstmt.close();// 瀵硅薄.鏂规硶
				 if(connection!=null)connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void query() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null ; 
		try {
			// a.导入驱动，加载具体的驱动类
			Class.forName("oracle.jdbc.OracleDriver");// 加载具体的驱动类
			// b.与数据库建立连接
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
			String sql = "select * from student";
			// c.发送sql，执行(增删改、【查】)
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
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
			while(rs.next()){
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
				if(rs!=null) rs.close(); 
				 if(pstmt!=null) pstmt.close();// 对象.方法
				 if(connection!=null)connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		//update() ;
		query() ;
	}
}
```

#####  Statement操作数据库：

* 增删改：executeUpdate()
* 查询：executeQuery();

#####  ResultSet：保存结果集 select * from xxx

* next():光标下移，判断是否有下一条数据；true/false
* previous():  true/false
* getXxx(字段名|位置):获取具体的字段值 

#####  PreparedStatement操作数据库：
是statement的子接口： public interface PreparedStatement extends Statement 

* 增删改：executeUpdate()
* 查询：executeQuery();
* 赋值操作 setXxx();

### Statement 与 PreparedStatement的区别：

##### PreparedStatement与Statement在使用时的区别：

###### Statement:

* sql
* executeUpdate(sql)
* 先有创建sql字符串，在执行executeUpdate(sql)。




######  PreparedStatement:

* sql(可能存在占位符?)
* 在创建PreparedStatement 对象时，将sql预编译 prepareStatement(sql)
* executeUpdate()
* setXxx()替换占位符？

#### 推荐使用PreparedStatement：原因如下：
**1.编码更加简便（避免了字符串的拼接）**

String name = "zs" ;
int age = 23 ;

**stmt:（Statement）**
String sql =" insert into student(stuno,stuname) values('"+name+"',  "+age+" )    " ;
stmt.executeUpdate(sql);

**pstmt:（PreparedStatement）**
String sql =" insert into student(stuno,stuname) values(?,?) " ;
pstmt = connection.prepareStatement(sql);//预编译SQL
pstmt.setString(1,name);
pstmt.setInt(2,age);

**2.提高性能(因为 有预编译操作，预编译只需要执行一次)**
**需求**：需要重复增加100条数 
**stmt**:
String sql =" insert into student(stuno,stuname) values('"+name+"',  "+age+" )    " ;
for(100)
stmt.executeUpdate(sql);

**pstmt:**
String sql =" insert into student(stuno,stuname) values(?,?) " ;
pstmt = connection.prepareStatement(sql);//预编译SQL
pstmt.setString(1,name);
pstmt.setInt(2,age);
for( 100){
pstmt.executeUpdate();
}

**3.安全（可以有效防止sql注入）**

* sql注入： 将客户输入的内容  和 开发人员的SQL语句 混为一体。
* stmt:存在被sql注入的风险  
  (例如输入  密码：任意值 ' or 1=1 --
  	   用户名：任意值)

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCDemo {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:MLDN";
	private static final String USERNAME = "c##scott";
	private static final String PWD = "tiger";
	
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
//			String sql = "select stuno,stuname from student";
			Scanner input= new Scanner(System.in);
			
			System.out.println("请输入用户名");
			String pwd = input.nextLine() ;
			System.out.println("请输入密码：");
			String name = input.nextLine() ;
			String sql = "select count(*) from login where id='"+name+"' and name ='"+pwd+"' " ;
//			String sql = "select * from student where stuname like '%"+name+"%'";
			// 执行SQL(增删改executeUpdate()，查询executeQuery())
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) rs.close(); 
				 if(stmt!=null) stmt.close();// 对象.方法
				 if(connection!=null)connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		query() ;
	}
}

```

在oracle数据库中存在一个login表，表中存在一个id为1，name为"zs"的条目。

上面程序执行时，用户名输入：随便输入。

​				密码：任意值 ' or 1=1 --

能够成功登陆！



分析：
select count(*) from login where uname='任意值 ' or 1=1 --' and upwd ='任意值'  ;由于--是oracle sql中的注释语句，于是等价于下面这句：
select count(*) from login where uname='任意值 ' or 1=1 ;于是永远都是true。

pstmt:有效防止sql注入

#### 推荐使用pstmt