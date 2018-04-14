/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.content.Group;
import com.controller.util.ControllerUtil;
import com.dao.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ServiceFactory;
import com.util.LogUtil;
import com.util.NumberUtil;
import com.util.TextUtil;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FindSenior.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 16, 2017$
 * @since 1.0
 */

@Controller
@RequestMapping("/group")
public class GroupController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(GroupController.class);
    GroupService groupService = ServiceFactory.getGroupService();
    @RequestMapping(value = "/getGroup", method = RequestMethod.GET)
    public @ResponseBody Group getGroup(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return groupService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public @ResponseBody String updateGroup(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Group group = mapper.readValue(jsonString, Group.class);
            Group groupdb = null;
            if (group.getId() != 0 
                    && group.getId() != null)
                groupdb = groupService.getById(group.getId());
            
            if (groupdb != null 
                    && groupdb.getId() != null 
                    && groupdb.getId() != 0)
            {
                groupdb.setName(group.getName());
                groupdb.setQq(group.getQq());
                groupdb.setQqCode(group.getQqCode());
                groupdb.setWeixin(group.getWeixin());
                groupdb.setWeixinCode(group.getWeixinCode());
                groupdb.setYear(group.getYear());
                groupdb.setSchoolId(group.getSchoolId());
                groupdb.setProfessionalId(groupdb.getProfessionalId());
                groupdb.setUpdateDate(new Date());
                groupService.updateGroup(groupdb);
            }
            else
            {
                group.setId(new Integer(0));
                group.setUpdateDate(new Date());
                groupService.createGroup(group);
            }
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/deleteGroup", method = RequestMethod.DELETE)
    public @ResponseBody String deleteGroup(@RequestParam String id)
    {
        try
        {
            Integer groupId = NumberUtil.parseInteger(id);
            if (groupId == null || groupId <= 0)
                return FAILED;
            
            Group group = groupService.getById(groupId);
            if (group == null)
                return FAILED;
            
            groupService.deleteGroup(group);
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        return FAILED;
    } 
    
    @RequestMapping(value = "/searchGroup", method = RequestMethod.GET)
    public @ResponseBody Group[] searchGroup(String province, String schoolId, String professionalId, String year, String name)
    {
        Group[] Groups = null;
        try
        {
            province = !TextUtil.isEmpty(schoolId) ? null : province;
            Groups = groupService.queryGroups(province, 
                    NumberUtil.parseInteger(schoolId), 
                    NumberUtil.parseInteger(professionalId), 
                    NumberUtil.parseInteger(year), name);
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
            return null;
        }
        return Groups;
    }
}
