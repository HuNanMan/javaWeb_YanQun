<%@page import="org.student.entity.Page"%>
<%@page import="org.student.entity.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("tr:odd").css("background-color","lightgray");
		});
	
	</script>
	

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息列表</title>
</head>
<body>
	<%
		/*
			在分页显示的前提下：显示数据jsp需要哪些数据：
					当前页  currentPage
					页面大小 pageSize
					当前页的数据集合  students
					总数据 totalCount
					总页数   totalPage
							-->新建Page类，用于封装以上5个字段
		*/
	
	
	
		//error:adderror 失败
		//否则：1 确实执行了增加    2直接访问查询全部页面
		String error = (String)request.getAttribute("error") ;//addError
		if(error!=null){
			if(error.equals("addError")){
				out.print("增加失败！");
			}else if(error.equals("noaddError")){
				out.print("增加成功！");
			}//根本没有执行增加
		}	
		
	%>


		<table border="1px">
			<tr>
				<th>学号</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>操作</th>
			</tr>
			
			<%
				//获取request域中的数据
				Page p  = (Page)request.getAttribute("p") ;
			
			
			
				for(Student student:p.getStudents()){
			%>
					<tr>
						<td><a href="QueryStudentBySnoServlet?sno=<%=student.getSno() %>"><%=student.getSno() %></a>      </td>
						
						
						<td><%=student.getSname() %></td>
						<td><%=student.getSage() %></td>
						<td> <a href="DeleteStudentServlet?sno=<%=student.getSno() %>   ">删除</a> </td>
						
					</tr>
			<%
				}
			
			
			%>
		
		</table>
		<a href="add.jsp">新增</a><br/>
		
		<%
				if(p.getCurrentPage() ==p.getTotalPage()){ //尾页
		%>			<a href="QueryStudentByPage?currentPage=1&pageSize=<%=p.getPageSize()%>">首页</a>
					<a href="QueryStudentByPage?currentPage=<%=p.getCurrentPage()-1%>&pageSize=<%=p.getPageSize()%>    ">上一页</a>
		<% 
				}
	
				else if(p.getCurrentPage() ==1){//首页
		%>		<a href="QueryStudentByPage?currentPage=<%=p.getCurrentPage()+1%>&pageSize=<%=p.getPageSize()%> ">下一页</a>
				<a href="QueryStudentByPage?currentPage=<%=p.getTotalPage()%>&pageSize=<%=p.getPageSize()%>">尾页</a>
		<%		
			}
				else{//中间
			%>		
					<a href="QueryStudentByPage?currentPage=1?pageSize=<%=p.getPageSize()%>">首页</a>
					<a href="QueryStudentByPage?currentPage=<%=p.getCurrentPage()-1%>&pageSize=<%=p.getPageSize()%>">上一页</a>
					<a href="QueryStudentByPage?currentPage=<%=p.getCurrentPage()+1%>&pageSize=<%=p.getPageSize()%> ">下一页</a>
					<a href="QueryStudentByPage?currentPage=<%=p.getTotalPage()%>&pageSize=<%=p.getPageSize()%>">尾页</a>
		
			<%			
				}
		
		
		%>
		
		
		<br/>
		
	<form action="QueryStudentByPage" >
	每页显示
		<select name="pageSize" id="pageSize">
			<option  value="3">3</option>
			<option  value="5">5</option>
			<option   value="10">10</option>
		</select>
		 条
		<input type="submit" value="修改页面大小" /><br/>
	</form>	
		
		
		
</body>
</html>