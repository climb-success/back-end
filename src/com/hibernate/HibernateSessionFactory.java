/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;  
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.util.LogUtil;

/**
 * HibernateSessionFactory.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public class HibernateSessionFactory 
{
    private static Logger logger = Logger.getLogger(HibernateSessionFactory.class);
    /** Hibernate的配置文件hibernate.cfg.xml */
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
    /** Session的线程本地变量 */
    private static final ThreadLocal threadLocal = new ThreadLocal();
    /** transaction的线程本地变量 */
    private static final ThreadLocal txThreadLocal = new ThreadLocal();
    private static Configuration configuration = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;
 
    private HibernateSessionFactory(){}
        
         /**
     * 获得一个线程本地化的Hibernate session实例
     * @return Session
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException 
    {
        Session session = (Session) threadLocal.get();
        if (session == null || !session.isOpen())
        {
            if (sessionFactory == null) 
                rebuildSessionFactory();
            session = (sessionFactory != null) ? sessionFactory.openSession() : null;
            threadLocal.set(session);
        }
 
        return session;
    }
 
    /**
    * Rebuild hibernate session factory
    */
    public static void rebuildSessionFactory() 
    {
       try 
       {
           configuration.configure(configFile);
           sessionFactory = configuration.buildSessionFactory();
       } 
       catch (Exception e) 
       {
           logger.error("Error Creating SessionFactory." + LogUtil.toString(e));
        }
    }
 
    /**
    * 关闭线程化的Hibernate session实例
    * @throws HibernateException
    */
    public static void closeSession() throws HibernateException 
    {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);
 
        if (session != null) 
            session.close();
    }
 
    /**
    * return session factory
    *
    */
    public static org.hibernate.SessionFactory getSessionFactory() 
    {
        return sessionFactory;
    }
 
    /**
    * return session factory
    * session factory will be rebuilded in the next call
    */
    public static void setConfigFile(String configFile) 
    {
        HibernateSessionFactory.configFile = configFile;
        sessionFactory = null;
    }
 
    /**
     * return hibernate configuration
     *
     */
    public static Configuration getConfiguration() 
    {
        return configuration;
    }

    /**
     * 在本地session实例上开启事务
    */
    public static void beginTransaction()
    {
        Transaction tx = (Transaction)txThreadLocal.get();
        if(tx == null)
        {
            tx = getSession().beginTransaction();
            txThreadLocal.set(tx);
        }
    }

    /**
    * 在本地session实例上提交事务
    */
    public static void commitTransaction()
    {
        Transaction tx = (Transaction)txThreadLocal.get();
        if(tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
        {
            tx.commit();
            txThreadLocal.set(null);
        }
    }

    /**
     * 在本地session实例上回滚事务
     */
    public static void rollbackTransaction()
    {
        Transaction tx = (Transaction)txThreadLocal.get();
        txThreadLocal.set(null);
        if(tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
        {
            tx.rollback();
        }
    }
} 
