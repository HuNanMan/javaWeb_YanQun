package org.student.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.student.entity.Student;
import org.student.service.IService;
import org.student.service.impl.ServiceImpl;

public class UpdateStudentServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		int no = Integer.parseInt(req.getParameter("sno"));
		String name = req.getParameter("sname");
		int age = Integer.parseInt(req.getParameter("sage"));
		String address=req.getParameter("saddress");
		Student student=new Student(no,name,age,address);
		System.out.println("update"+student);
		
		IService service = new ServiceImpl();
		boolean flag = service.updateStudentBySno(no,student);
		if(flag)
			System.out.println("修改成功");
		else {
			System.out.println("修改失败");
		}
		req.getRequestDispatcher("QueryAllStudentsServlet").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
