package org.student.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.student.entity.Student;
import org.student.service.IService;
import org.student.service.impl.ServiceImpl;
import org.student.dao.*;
public class Add  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		int no = Integer.parseInt(req.getParameter("sno"));
		String name = req.getParameter("sname");
		int age = Integer.parseInt(req.getParameter("sage"));
		String address=req.getParameter("saddress");
		Student student=new Student(no,name,age,address);
		System.out.println(student);
		
		IService service = new ServiceImpl();
		boolean flag = service.addStudent(student);
		if(flag) {
			req.setAttribute("error","noaddError");
		}else {
			req.setAttribute("error","addError");
		}
		req.getRequestDispatcher("QueryAllStudentsServlet").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
