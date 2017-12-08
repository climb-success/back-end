/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Student;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoStudent extends DaoService implements StudentService
{
    public Student createStudent(Student student)
    {
        return (Student) create(student);
    }
    
    public Student updateStudent(Student student)
    {
        return (Student) update(student);
    }
    
    public Student getById(Integer id)
    {
        return (Student) find(Student.class, id);
    }
    
    public void deleteStudent(Student student)
    {
        delete(student);
    }
    
    public Student[] queryStudents(String name, String school, String professional, String requirement, Integer grade,String status)
    {
        Student[] students = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        args.put("school", TextUtil.isEmpty(school) ? null : "%" + school + "%");
        args.put("professional", TextUtil.isEmpty(professional) ? null : "%" + professional + "%");
        args.put("requirement", TextUtil.isEmpty(requirement) ? null : "%" + requirement + "%");
        args.put("grade", grade == null ? null : grade);
        args.put("status", TextUtil.isEmpty(status) ? null : "%" + status + "%");
        Object[] result = query("STUDENT.QUERY_BY_NAME_SCHOOL_PROFESSION_REQUIREMENT_GRADE_STATUS", args);
        if (result == null)
            return null;
        
        students = new Student[result.length];
        for (int i = 0; i < result.length; i ++)
            students[i] = (Student) result[i];
        
        return students;
    }

    /**
     * @see com.dao.StudentService#queryStudentsByTelePhone(java.lang.String)
     */
    @Override
    public Student queryStudentByTelePhone(String telePhone) 
    {
        Map args = new HashMap<>();
        args.put("telePhone", telePhone);
        Object[] result = query("STUDENT.QUERY_BY_TELEPHONE", args);
        if (result == null || result.length == 0)
            return null;
        
        return (Student) result[0];
    }
}
