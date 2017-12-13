/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Student;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface StudentService
{
    public Student createStudent(Student student);
    
    public Student updateStudent(Student student);
    
    public Student getById(Integer id);
    
    public void deleteStudent(Student student);
    
    public Student[] queryStudents(String name, String school, String professional, String requirement, Integer grade, String status);

    public Student queryStudentByTelePhone(String telePhone);
    
    public boolean sendStudentEmail(Student student);
}
