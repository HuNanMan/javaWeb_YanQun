package org.student.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.student.entity.Page;
import org.student.entity.Student;
import org.student.service.IService;
import org.student.service.impl.ServiceImpl;

import jdk.nashorn.internal.ir.RuntimeNode.Request;



public class QueryStudentsByPageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		Page page = new Page();
		int currentPage = 1 ;
		int pageSize  =2 ;
	    String cPage = req.getParameter("currentPage");
		if(cPage!=null) {
			 currentPage = Integer.parseInt(cPage);
		}
		String cSize =req.getParameter("pageSize");
		if(cSize!=null) {
			pageSize = Integer.parseInt(cSize);
		}
		
		
		IService service = new ServiceImpl();
		List<Student> students = new ArrayList<>();
		students = service.queryStudentsByPage(currentPage, pageSize);
		int totalCount = service.getTotalCount();
		
		page.setCurrentPage(currentPage);
		page.setStudents(students);
		page.setTotalCount(totalCount);
		page.setPageSize(pageSize);
		req.setAttribute("p",page);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
