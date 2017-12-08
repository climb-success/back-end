/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.util.LogUtil;

/**
 * AbstractDaoService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public class DaoService
{
    private static Logger logger = Logger.getLogger(DaoService.class);
    public Object create(Object obj)
    {
        try
        {
            Session session = HibernateSessionFactory.getSession();
            HibernateSessionFactory.beginTransaction();
            session.save(obj);  
            HibernateSessionFactory.commitTransaction();
        }
        catch (Exception e)
        {
            logger.error("Creat the db fail." + LogUtil.toString(e));
            throw e;
        }
        finally 
        {
            HibernateSessionFactory.closeSession();
        }
        
        return obj;
    }
    
    public Object find(Class clazz, Integer id)
    {
        Object result = null;
        try
        {
            Session session = HibernateSessionFactory.getSession();
            result = session.get(clazz, id);
        }
        catch (Exception e)
        {
            logger.error("Find the db fail." + LogUtil.toString(e));
            throw e;
        }
        finally 
        {
            HibernateSessionFactory.closeSession();
        }
        
        return result;
    }
    
    public Object update(Object obj)
    {
        try 
        {
            Session session = HibernateSessionFactory.getSession();
            HibernateSessionFactory.beginTransaction();
            session.saveOrUpdate(obj);
 
            HibernateSessionFactory.commitTransaction();
        } 
        catch (Exception e) 
        {
            HibernateSessionFactory.rollbackTransaction();
            logger.error("Update the db fail." + LogUtil.toString(e));
            throw e;
        } 
        finally 
        {
            HibernateSessionFactory.closeSession();
        }
        
        return obj;
    }
    
    public void delete(Object obj)
    {
        try 
        {
            Session session = HibernateSessionFactory.getSession();
            HibernateSessionFactory.beginTransaction();
            session.delete(obj);
            HibernateSessionFactory.commitTransaction();
        }
        catch (Exception e) 
        {
            HibernateSessionFactory.rollbackTransaction();
            logger.error("Delete the db fail" + LogUtil.toString(e));
            throw e;
        } 
        finally 
        {
            HibernateSessionFactory.closeSession();
        }
    }
    
    public Object[] query(String name, Map<String, Object> args)
    {
        Object[] result = null;
        try 
        {
            Session session = HibernateSessionFactory.getSession();
            Query query = session.getNamedQuery(name);
            for(Map.Entry<String, Object> entry: args.entrySet())
            {    
                if (entry.getValue() instanceof String)
                    query.setString(entry.getKey(), (String) entry.getValue());
                else if (entry.getValue() instanceof Integer)
                    query.setInteger(entry.getKey(), (Integer) entry.getValue());
                else if (entry.getValue() instanceof Date)
                    query.setDate(entry.getKey(), (Date) entry.getValue());
                else if (entry.getValue() instanceof Object[])
                    query.setParameterList(entry.getKey(), (Object[]) entry.getValue());
                else
                    query.setParameter(entry.getKey(), entry.getValue());
            }
            
            List<Object> list = query.list();
            result = list.toArray(new Object[list.size()]);
        }
        catch (Exception e) 
        {
            logger.error("Select the db fail" + LogUtil.toString(e));
            throw e;
        } 
        finally 
        {
            HibernateSessionFactory.closeSession();
        }
        
        return result;
    }
    
    public Object[] query(String name, Map<String, Object> args, int start, int max)
    {
        Object[] result = query(name, args);
        
        if (result == null 
                || start >= result.length -1)
            return result;
        
        List<Object> list = new ArrayList<Object>();
        for (int i = start ; i < max; i ++)
        {
            if (i < result.length -1)
                list.add(result[i]);
        }
        
        return result;
    }
}
