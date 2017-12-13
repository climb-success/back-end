/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Administrator;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoAdmin.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 20, 2017$
 * @since 1.0
 */
public class DaoAdmin extends DaoService implements AdminService
{

    /**
     * @see com.dao.AdminService#createAdmin(com.content.Administrator)
     */
    @Override
    public Administrator createAdmin(Administrator ad)
    {
        return (Administrator) create(ad);
    }

    /**
     * @see com.dao.AdminService#updateAdmin(com.content.Administrator)
     */
    @Override
    public Administrator updateAdmin(Administrator ad)
    {
        return (Administrator) update(ad);
    }

    /**
     * @see com.dao.AdminService#getById(java.lang.Integer)
     */
    @Override
    public Administrator getById(Integer id)
    {
        return (Administrator) getById(id);
    }

    /**
     * @see com.dao.AdminService#deleteAdmin(com.content.Administrator)
     */
    @Override
    public void deleteAdmin(Administrator ad)
    {
        delete(ad);
    }

    /**
     * @see com.dao.AdminService#validateAdmin(java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateAdmin(Integer id, String name, String password)
    {
        if (id == null 
                || TextUtil.isEmpty(name) 
                || TextUtil.isEmpty(password))
            return false;
        
        Map args = new HashMap<>();
        args.put("id", id);
        args.put("name", name);
        args.put("password", password);
        Object[] result = query("ADMINISTRATOR.VALIDATE", args);
        if (result == null 
                || result.length == 0)
            return true;
        
        return false;
    }

    /**
     * @see com.dao.AdminService#validateAdmin(java.lang.String)
     */
    @Override
    public boolean validateAdmin(String name)
    {
        if (TextUtil.isEmpty(name))
            return false;
        
        Map args = new HashMap<>();
        args.put("name", name);
        Object[] result = query("ADMINISTRATOR.VALIDATE_NAME", args);
        if (result != null 
                && result.length != 0)
            return true;
        
        return false;
    }

    /**
     * @see com.dao.AdminService#validateAdmin(java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateAdmin(String name, String password)
    {
        if (TextUtil.isEmpty(name) 
                || TextUtil.isEmpty(password))
            return false;
        
        Map args = new HashMap<>();
        args.put("name", name);
        args.put("password", password);
        Object[] result = query("ADMINISTRATOR.VALIDATE_NAME_PASSWORD", args);
        if (result != null 
                && result.length != 0)
            return true;
        
        return false;
    }

    /**
     * @see com.dao.AdminService#getAllAdmin()
     */
    @Override
    public Administrator[] getAllAdmin()
    {
        Administrator[] admins = null;
        Map args = new HashMap<>();
        Object[] result = query("ADMINISTRATOR.QUERY_ALL", args);
        if (result == null 
                || result.length == 0)
            return null;
        
        admins = new Administrator[result.length];
        for (int i = 0; i < result.length; i ++)
            admins[i] = (Administrator) result[i];
        return admins;
    }
}
