/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.content.SchoolProfessional;
import com.hibernate.DaoService;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoSchoolProfessional extends DaoService implements SchoolProfessionalService
{
    public SchoolProfessional createSchoolProfessional(SchoolProfessional schoolProfessional)
    {
        schoolProfessional.setUpdateDate(new Date());
        return (SchoolProfessional) create(schoolProfessional);
    }
    
    public SchoolProfessional updateSchoolProfessional(SchoolProfessional schoolProfessional)
    {
        schoolProfessional.setUpdateDate(new Date());
        return (SchoolProfessional) update(schoolProfessional);
    }
    
    /**
     * @see com.dao.SchoolProfessionalService#updateSchoolProfessional(com.content.SchoolProfessional)
     */
    @Override
    public void updateSchoolProfessionals(Integer schoolId, List<SchoolProfessional> schoolProfessionals)
    {
        SchoolProfessional[] schoolProfessionalDBs = getSchoolProfessionalsBySchoolId(schoolId);
        
        if (schoolProfessionals == null 
                ||schoolProfessionals.size() == 0)
        {
            for (SchoolProfessional sp : schoolProfessionalDBs)
                delete(sp);
        }
        
        List<SchoolProfessional> delete = new ArrayList<SchoolProfessional>();
        List<SchoolProfessional> add = new ArrayList<SchoolProfessional>();
        
        for (SchoolProfessional spdb : schoolProfessionalDBs)
        {
            boolean isDelete = true;
            for (SchoolProfessional sp : schoolProfessionals)
            {
                if (sp.getProfessionalId().equals(spdb.getProfessionalId()))
                {
                    isDelete = false;
                    break;
                }
            }
            
            if (isDelete)
                delete.add(spdb);
            
        }
        
        for (SchoolProfessional sp : schoolProfessionals)
        {
            boolean isAdd = true;
            for (SchoolProfessional spdb : schoolProfessionalDBs)
            {
                if (sp.getProfessionalId().equals(spdb.getProfessionalId()))
                {
                    isAdd = false;
                    break;
                }
            }
            
            if (isAdd)
                add.add(sp);
        }
        
        for (SchoolProfessional sp : delete)
            deleteSchoolProfessional(sp);
        
        for (SchoolProfessional sp : add)
        {
            sp.setSchoolId(schoolId);
            createSchoolProfessional(sp);
        }
    }
    
    public SchoolProfessional getById(Integer id)
    {
        return (SchoolProfessional) find(SchoolProfessional.class, id);
    }
    
    public void deleteSchoolProfessional(SchoolProfessional schoolProfessional)
    {
        delete(schoolProfessional);
    }
    
    public SchoolProfessional[] querySchoolProfessionals(Integer shoolId, String schoolName, String name)
    {
        /*SchoolProfessional[] professionals = null;
        Map args = new HashMap<>();
        args.put("schoolId", shoolId == null || shoolId == 0 ? null : shoolId);
        args.put("schoolName", TextUtil.isEmpty(schoolName) ? null : "%" + schoolName + "%");
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        Object[] result = query("PROFESSIONAL.QUERY_BY_SCHOOL_NAME", args);
        if (result == null)
            return null;
        
        professionals = new Professional[result.length];
        for (int i = 0; i < result.length; i ++)
            professionals[i] = (Professional) result[i];*/
        
        return null;
    }
    
    public SchoolProfessional[] getSchoolProfessionalsBySchoolId(Integer schoolId)
    {
        SchoolProfessional[] schoolProfessionals = null;
        if (schoolId == null)
            return null;
        Map args = new HashMap<>();
        args.put("schoolId", schoolId == null || schoolId == 0 ? null : schoolId);
        Object[] result = query("SCHOOLPROFESSIONAL.QUERY_BY_SCHOOLID", args);
        if (result == null)
            return new SchoolProfessional[0];
        
        schoolProfessionals = new SchoolProfessional[result.length];
        for (int i = 0; i < result.length; i ++)
            schoolProfessionals[i] = (SchoolProfessional) result[i];
        
        return schoolProfessionals;
    }
}
