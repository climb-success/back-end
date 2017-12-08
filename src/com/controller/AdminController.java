/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.content.Administrator;
import com.controller.util.ControllerUtil;
import com.dao.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ServiceFactory;
import com.util.LogUtil;
import com.util.NumberUtil;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FindSenior.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 16, 2017$
 * @since 1.0
 */

@Controller
@RequestMapping("/admin")
public class AdminController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(AdminController.class);
    AdminService adminService = ServiceFactory.getAdminService();
    
/*    @RequestMapping(value = "/getAdmin", method = RequestMethod.GET)
    public @ResponseBody Administrator getAdmin(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return adminService.getById(idNow);
    }*/
    
    @RequestMapping(value = "/updateAdminHHH", method = RequestMethod.POST)
    public @ResponseBody String updateAdmin(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Administrator admin = mapper.readValue(jsonString, Administrator.class);
            
            if (admin.getId() == 0)
                adminService.createAdmin(admin);
            else
                adminService.updateAdmin(admin);
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        return FAILED;
    }
    
    @RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
    public @ResponseBody String loginAdmin(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Administrator admin = mapper.readValue(jsonString, Administrator.class);
            boolean login = validateAdmin(admin.getName(), admin.getPassword());
            if (login)
                return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        return FAILED;
    }
    
    @RequestMapping(value = "/deleteAdmin", method = RequestMethod.DELETE)
    public @ResponseBody String deleteAdmin(@RequestParam String id)
    {
        try
        {
            Integer idNow = NumberUtil.parseInteger(id);
            if (idNow == null)
                return FAILED;
            
            Administrator admin = adminService.getById(idNow);
            if (admin != null)
                adminService.deleteAdmin(admin);
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
}
