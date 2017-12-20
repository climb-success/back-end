/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Professional;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoProfessional extends DaoService implements ProfessionalService
{
    public Professional createProfessional(Professional professional)
    {
        return (Professional) create(professional);
    }
    
    public Professional updateProfessional(Professional professional)
    {
        return (Professional) update(professional);
    }
    
    public Professional getById(Integer id)
    {
        return (Professional) find(Professional.class, id);
    }
    
    public void deleteProfessional(Professional professional)
    {
        delete(professional);
    }

    @Override
    public Professional[] getAllProfessional()
    {
        Professional[] professionals = null;
        Map args = new HashMap<>();
        Object[] result = query("PROFESSIONAL.QUERY_ALL", args);
        if (result == null)
            return new Professional[0];
        
        professionals = new Professional[result.length];
        for (int i = 0; i < result.length; i ++)
            professionals[i] = (Professional) result[i];
        
        return professionals;
    }

    /**
     * @see com.dao.ProfessionalService#getProfessionalByName(java.lang.String)
     */
    @Override
    public Professional getProfessionalByName(String name)
    {
        Professional professional = null;
        Map args = new HashMap<>();
        args.put("name", name);
        Object[] result = query("PROFESSIONAL.QUERY_BY_NAME", args);
        if (result == null || result.length == 0)
            return null;
        
        return (Professional) result[0];
    }

    /**
     * @see com.dao.ProfessionalService#getProfessionalByCode(java.lang.String)
     */
    @Override
    public Professional getProfessionalByCode(String code)
    {
        Professional professional = null;
        Map args = new HashMap<>();
        args.put("code", code);
        Object[] result = query("PROFESSIONAL.QUERY_BY_CODE", args);
        if (result == null || result.length == 0)
            return null;
        
        return (Professional) result[0];
    }
}
