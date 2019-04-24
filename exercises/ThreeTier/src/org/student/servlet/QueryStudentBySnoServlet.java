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

/**
 * Servlet implementation class QueryStudentBySnoServlet
 */
@WebServlet("/QueryStudentBySnoServlet")
public class QueryStudentBySnoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		int no = Integer.parseInt(req.getParameter("sno"));
		IService service = new ServiceImpl();
		Student student = service.queryStudentBySno(no);
		System.out.println("laole "+student);
		req.setAttribute("student",student);
		req.getRequestDispatcher("studentInfo.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
