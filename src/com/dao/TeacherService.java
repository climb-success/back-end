/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Teacher;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface TeacherService
{
    public Teacher createTeacher(Teacher te);
    
    public Teacher updateTeacher(Teacher te);
    
    public Teacher getById(Integer id);
    
    public void deleteTeacher(Teacher te);
    
    public Teacher[] queryTeachers(String name, String school, String professional, String requirement, Integer grade);

    public Teacher queryTeacherByTelePhone(String telePhone);
}
