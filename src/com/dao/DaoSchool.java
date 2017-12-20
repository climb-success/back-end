/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.School;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoSchool extends DaoService implements SchoolService
{
    public School createSchool(School school)
    {
        return (School) create(school);
    }
    
    public School updateSchool(School school)
    {
        return (School) update(school);
    }
    
    public School getById(Integer id)
    {
        return (School) find(School.class, id);
    }
    
    public void deleteSchool(School te)
    {
        delete(te);
    }
    
    public School[] querySchools(String name, String province)
    {
        School[] Schools = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        args.put("province", TextUtil.isEmpty(province) ? null : "%" + province + "%");
        Object[] result = query("SCHOOL.QUERY_BY_NAME_PROVINCE", args);
        if (result == null)
            return null;
        
        Schools = new School[result.length];
        for (int i = 0; i < result.length; i ++)
            Schools[i] = (School) result[i];
        
        return Schools;
    }

    /**
     * @see com.dao.SchoolService#getAllSchools()
     */
    @Override
    public School[] getAllSchools()
    {
        School[] schools = null;
        Map args = new HashMap<>();
        Object[] result = query("SCHOOL.QUERY_ALL", args);
        if (result == null)
            return new School[0];
        
        schools = new School[result.length];
        for (int i = 0; i < result.length; i ++)
            schools[i] = (School) result[i];
        
        return schools;
    }
    
    /**
     * @see com.dao.SchoolProfessionalService#querySchool(java.lang.String, java.lang.String)
     */
    @Override
    public School querySchool(String name, String province)
    {
        School[] schools = querySchools(name, province);
        if (schools == null || schools.length == 0)
            return null;
        
        return (School) schools[0];
    }
}
