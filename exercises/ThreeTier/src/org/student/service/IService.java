package org.student.service;

import java.util.List;

import org.student.entity.Student;

public interface IService {

	public Student queryStudentBySno(int sno);
	//查询全部学生
	public List<Student> queryAllStudents();
	//查询当前页的学生
	public List<Student> queryStudentsByPage(int currentPage,int pageSize);
	
	//查询总数
	public int getTotalCount();
	
	
	
	public boolean updateStudentBySno(int sno, Student student) ;
	
	public boolean deleteStudentBySno(int sno) ;
	
	public boolean addStudent(Student student) ;
}
