/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Teacher;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoTeacher extends DaoService implements TeacherService
{
    public Teacher createTeacher(Teacher te)
    {
        return (Teacher) create(te);
    }
    
    public Teacher updateTeacher(Teacher te)
    {
        return (Teacher) update(te);
    }
    
    public Teacher getById(Integer id)
    {
        return (Teacher) find(Teacher.class, id);
    }
    
    public void deleteTeacher(Teacher te)
    {
        delete(te);
    }
    
    public Teacher[] queryTeachers(String name, String school, String professional, String requirement, Integer grade)
    {
        Teacher[] teachers = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        args.put("school", TextUtil.isEmpty(school) ? null : "%" + school + "%");
        args.put("professional", TextUtil.isEmpty(professional) ? null : "%" + professional + "%");
        args.put("requirement", TextUtil.isEmpty(requirement) ? null : "%" + requirement + "%");
        args.put("grade", grade);
        Object[] result = query("TEACHER.QUERY_BY_NAME_SCHOOL_PROFESSION_REQUIREMENT", args);
        if (result == null)
            return null;
        
        teachers = new Teacher[result.length];
        for (int i = 0; i < result.length; i ++)
            teachers[i] = (Teacher) result[i];
        
        return teachers;
    }

    /**
     * @see com.dao.TeacherService#queryTeacherByTelePhone(java.lang.String)
     */
    @Override
    public Teacher queryTeacherByTelePhone(String telePhone)
    {
        Map args = new HashMap<>();
        args.put("telePhone", telePhone);
        Object[] result = query("TEACHER.QUERY_BY_TELEPHONE", args);
        if (result == null || result.length == 0)
            return null;
        
        return (Teacher) result[0];
    }
}
