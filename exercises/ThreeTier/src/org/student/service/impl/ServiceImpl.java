package org.student.service.impl;

import java.util.List;

import org.student.dao.IStudentDao;
import org.student.dao.impl.StudentDaoImpl;
import org.student.entity.Student;
import org.student.service.IService;

public class ServiceImpl implements IService{
	IStudentDao temp = new StudentDaoImpl();
	@Override
	public Student queryStudentBySno(int sno) {
		return  temp.queryStudentBySno(sno);
	}

	@Override
	public List<Student> queryAllStudents() {
		return temp.queryAllStudents();
	}

	@Override
	public List<Student> queryStudentsByPage(int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return temp.queryStudentsByPage(currentPage, pageSize);
	}

	@Override
	public int getTotalCount() {
		return temp.getTotalCount();
	}

	@Override
	public boolean updateStudentBySno(int sno, Student student) {
		return temp.updateStudentBySno(sno, student);
	}

	@Override
	public boolean deleteStudentBySno(int sno) {
		return temp.deleteStudentBySno(sno);
	}

	@Override
	public boolean addStudent(Student student) {
		return temp.addStudent(student);
	}
}
