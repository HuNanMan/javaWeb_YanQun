### jdbc总结（模板、八股文）：

try{

* 导入驱动包、加载具体驱动类Class.forName("具体驱动类");

* 与数据库建立连接connection = DriverManager.getConnection(...);

* 通过connection，获取操作数据库的对象（Statement\preparedStatement\callablestatement）

* stmt = connection.createStatement();

* (查询)处理结果集rs = pstmt.executeQuery()

  

  while(rs.next()){ rs.getXxx(..) ;}
  }catch(ClassNotFoundException e  )
  { ...}
  catch(SQLException e)
  {...
  }
  catch(Exception e)
  {...
  }
  finally
  {
  	//打开顺序，与关闭顺序相反
  	if(rs!=null)rs.close()
  	if(stmt!=null) stmt.close();
  	if(connection!=null)connection.close();
  }

**jdbc中，除了Class.forName() 抛出ClassNotFoundException，其余方法全部抛SQLException**

