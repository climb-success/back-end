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
    public Teacher createTeacher(Teacher teacher);
    
    public Teacher updateTeacher(Teacher teacher);
    
    public Teacher getById(Integer id);
    
    public void deleteTeacher(Teacher teacher);
    
    public Teacher[] queryTeachers(String name, Integer school, Integer professionalId, String telePhone, 
            String requirement, Integer grade, String province);

    public Teacher queryTeacherByTelePhone(String telePhone);
}
