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

import com.content.Teacher;
import com.controller.util.ControllerUtil;
import com.dao.TeacherService;
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
@RequestMapping("/teacher")
public class TeacherController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(TeacherController.class);
    TeacherService teacherService = ServiceFactory.getTeacherService();
    @RequestMapping(value = "/getTeacher", method = RequestMethod.GET)
    public @ResponseBody Teacher getTeacher(@RequestParam String id, @RequestParam String adminName)
    {
        if (validateAdmin(adminName))
        {
            Integer idNow = NumberUtil.parseInteger(id);
            if (idNow == null)
                return null;
            return teacherService.getById(idNow);
        }
        return null;
    }
    
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public @ResponseBody String updateTeacher(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Teacher teacher = mapper.readValue(jsonString, Teacher.class);
            teacher.setUpdateDate(new Date());
            Teacher teacherdb = teacherService.queryTeacherByTelePhone(teacher.getTelePhone());
            
            if (teacherdb != null)
            {
                teacherdb.setName(teacher.getName());
                teacherdb.setSchoolId(teacher.getSchoolId());
                teacherdb.setProfessionalId(teacherdb.getProfessionalId());
                teacherdb.setTelePhone(teacher.getTelePhone());
                teacherdb.setQq(teacher.getQq());
                teacherdb.setWeixin(teacher.getWeixin());
                teacherdb.setScore(teacher.getScore());
                teacherdb.setRequirement(teacher.getRequirement());
                teacherdb.setGrade(teacher.getGrade());
                teacherdb.setUpdateDate(new Date());
                teacherService.updateTeacher(teacherdb);
            }
            else
            {
                teacher.setId(new Integer(0));
                teacher.setUpdateDate(new Date());
                teacherService.createTeacher(teacher);
            }
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/searchTeacher", method = RequestMethod.GET)
    public @ResponseBody Teacher[] searchTeacher(@RequestParam String adminName, String name, 
            String school, String professional, String requirement, String grade)
    {
        Teacher[] Teachers = null;
        
        if (validateAdmin(adminName))
        {
            try
            {
                Teachers = teacherService.queryTeachers(name, school, professional, requirement, NumberUtil.parseInteger(grade));
            }
            catch (Exception e)
            {
                logger.error(LogUtil.toString(e));
                return null;
            }
        }
        return Teachers;
    }
}
