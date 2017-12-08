/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.controller.util;

import com.dao.AdminService;
import com.service.ServiceFactory;
import com.util.NumberUtil;

/**
 * AbstractControllerAction.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 20, 2017$
 * @since 1.0
 */
public abstract class ControllerUtil
{
    protected final String SUCCESS = "success";
    protected final String FAILED = "failed";
    protected final String INVALID = "invalid";
    protected final String EXIST = "exist";
    
    AdminService adminService = ServiceFactory.getAdminService();
    /**
     * @param groupId
     * @param passWord
     * @return
     */
    public boolean validateAdmin(String adminId, String adminName, String password)
    {
        return adminService.validateAdmin(NumberUtil.parseInteger(adminId), adminName, password);
    }
    
    /**
     * @param adminName
     * @return
     */
    public boolean validateAdmin(String adminName)
    {
        return adminService.validateAdmin(adminName);
    }
    
    public boolean validateAdmin(String adminName, String password)
    {
        return adminService.validateAdmin(adminName, password);
    }
}
