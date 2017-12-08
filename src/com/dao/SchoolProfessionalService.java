/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.List;

import com.content.SchoolProfessional;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface SchoolProfessionalService
{
    public SchoolProfessional createSchoolProfessional(SchoolProfessional schoolProfessional);
    
    public SchoolProfessional updateSchoolProfessional(SchoolProfessional schoolProfessional);
    
    public void updateSchoolProfessionals(Integer schoolId, List<SchoolProfessional> schoolProfessionals);
    
    public SchoolProfessional getById(Integer id);
    
    public void deleteSchoolProfessional(SchoolProfessional schoolProfessional);
    
    public SchoolProfessional[] querySchoolProfessionals(Integer schoolId, String schoolName, String name);

    public SchoolProfessional[] getSchoolProfessionalsBySchoolId(Integer schoolId);
}
