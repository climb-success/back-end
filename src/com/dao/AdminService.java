/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Administrator;

/**
 * AdminService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 20, 2017$
 * @since 1.0
 */
public interface AdminService
{
    public Administrator createAdmin(Administrator ad);
    
    public Administrator updateAdmin(Administrator ad);
    
    public Administrator getById(Integer id);
    
    public void deleteAdmin(Administrator ad);
    
    public boolean validateAdmin(Integer id, String name, String password);
    
    public boolean validateAdmin(String name);
    
    public boolean validateAdmin(String name, String password);
}
