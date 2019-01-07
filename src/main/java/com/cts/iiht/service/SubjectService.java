package com.cts.iiht.service;


import java.util.List;

import com.cts.iiht.dao.SubjectDao;
import com.cts.iiht.entity.Subject;

public class SubjectService {
	
	SubjectDao subjectDao = new SubjectDao();
	
	
	public void addSubject(Subject subject) {
		subjectDao.addSubject(subject);
	}
	
	public void deleteSubject(long id) {
		subjectDao.deleteSubject(id);
	}

	public Subject searchSubject(long id) {
		return subjectDao.searchSubject(id);
	}
	public List<Subject> getAllSubjects() {
		return subjectDao.getAllSubjects();
	}

	public List<Subject> getAllSubjectsSortBySubTitle(){
		return subjectDao.getAllSubjectsSortBySubTitle();
	}
}
