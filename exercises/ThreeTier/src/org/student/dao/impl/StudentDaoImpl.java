package org.student.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.student.dao.IStudentDao;
import org.student.entity.Student;
import org.student.util.DButil;

public class StudentDaoImpl implements IStudentDao{

	@Override
	public boolean addStudent(Student student) {
		String sql = "insert into student values(?,?,?,?)";
		Object []params ={student.getSno(),student.getSname(),student.getSage(),student.getSaddress()};
		return DButil.executeUpdate(sql, params);
	}

	@Override
	public boolean updateStudentBySno(int sno, Student student) {
		if(isExist(sno)){
			if(deleteStudentBySno(sno))
				return addStudent(student);
			return false;
		}
		return false;
	}

	@Override
	public int getTotalCount() {
		String sql = "select count(*) from student";
		DButil.executeQuery(sql, null);
		try {
			if(DButil.set.next())
				return DButil.set.getInt(1);
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean deleteStudentBySno(int sno) {
		if(isExist(sno)){
				String sql = "delete from student where sno =?";
				Object []params = {sno};
				return DButil.executeUpdate(sql, params);
		}
		return false;
	}

	@Override
	public List<Student> queryAllStudents() {
		String sql = "select * from student";
		DButil.executeQuery(sql, null);
		List<Student> students =new ArrayList<>();
		try {
			while(DButil.set.next()){
				int no = DButil.set.getInt("sno");
				String name = DButil.set.getString("name");
				int age = DButil.set.getInt("age");
				String address = DButil.set.getString("address");
				Student student = new Student(no,name,age,address);
				students.add(student);
			}
			return students;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Student> queryStudentsByPage(int currentPage, int pageSize) {
		
		/*String sql = "select *from "+
				"("+
			    "select rownum r, t.* from"+
				"(select * from student  order by sno asc) t "+
			    
				"where rownum<=?"+
				")"+
				 "where r>=?";
				 */
				
		String sql =	" select * from "+
				 "("+
				" select rownum  r, t.* from "+
				
				"(select * from student  order by sno asc) t "+
				
				" where rownum<=? "+
				")"+
				" where r>=? ";
		/*
		String sql = "select * from "+
					 "(select rownum  r,t.* from"+
					"(select * from student order by sno asc) t"+
						"where rownum<=?)"+
						"where r>?";
		
		String sql = "select *from "
				+"("
			    +"select rownum r, t.* from"
				+"(select s.* from student s order by sno asc) t "
			    
				+"where rownum<=?"
				+")"
				+ "where r>=?"
			 ;
			 */
		Object []params= {currentPage*pageSize,(currentPage-1)*pageSize+1};
		DButil.executeQuery(sql,params);
		List<Student> students = new ArrayList<>();
		try {
			while(DButil.set.next()) {
				Student student = new Student(DButil.set.getInt("sno"),DButil.set.getString("name"),DButil.set.getInt("age"),DButil.set.getString("address"));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public boolean isExist(int sno) {
		String sql = "select * from student where sno=?";
		Object []params ={sno};
		
		DButil.set =DButil.executeQuery(sql,params);
		if(DButil.set!=null)
			return true;
		return false;
	}

	@Override
	public Student queryStudentBySno(int sno) {
		if(isExist(sno)){
			try {
				if(DButil.set.next()) {
					int no = DButil.set.getInt("sno");
					String name = DButil.set.getString("name");
					int age = DButil.set.getInt("age");
					String address = DButil.set.getString("address");
					Student student = new Student(no,name,age,address);
					return student;
				}
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
