/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.School;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface SchoolService
{
    public School createSchool(School school);
    
    public School updateSchool(School school);
    
    public School getById(Integer id);
    
    public void deleteSchool(School school);
    
    public School[] querySchools(String name, String province);

    public School[] getAllSchools();

    /**
     * @param name
     * @param province
     * @return
     */
    public School querySchool(String name, String province);
}
