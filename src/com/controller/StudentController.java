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

import com.content.Student;
import com.controller.util.ControllerUtil;
import com.dao.StudentService;
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
@RequestMapping("/student")
public class StudentController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(StudentController.class);
    StudentService studentService = ServiceFactory.getStudentService();
    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public @ResponseBody Student getStudent(@RequestParam String id, @RequestParam String adminName)
    {
        if (validateAdmin(adminName))
        {
            Integer idNow = NumberUtil.parseInteger(id);
            if (idNow == null)
                return null;
            return studentService.getById(idNow);
        }
        return null;
    }
    
    @RequestMapping(value = "/adminStudent", method = RequestMethod.GET)
    public @ResponseBody Student getAdminStudent(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return studentService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public @ResponseBody String updateStudent(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Student student = mapper.readValue(jsonString, Student.class);
            Student studentdb = studentService.queryStudentByTelePhone(student.getTelePhone());
            
            if (studentdb != null)
            {
                studentdb.setName(student.getName());
                studentdb.setSchoolId(student.getSchoolId());
                studentdb.setProfessionalId(student.getProfessionalId());
                studentdb.setTelePhone(student.getTelePhone());
                studentdb.setQq(student.getQq());
                studentdb.setWeixin(student.getWeixin());
                studentdb.setRequirement(student.getRequirement());
                studentdb.setGrade(student.getGrade());
                studentdb.setStatus(TextUtil.isEmpty(student.getStatus()) ? Student.NOT_FINISH : student.getStatus());
                studentdb.setUpdateDate(new Date());
                studentService.updateStudent(studentdb);
                studentService.sendStudentEmail(studentdb);
            }
            else
            {
                student.setId(new Integer(0));
                student.setStatus(Student.NOT_FINISH);
                student.setUpdateDate(new Date());
                studentService.createStudent(student);
                studentService.sendStudentEmail(student);
            }
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.DELETE)
    public @ResponseBody String deleteStudent(@RequestParam String id)
    {
        try
        {
            Integer studentId = NumberUtil.parseInteger(id);
            if (studentId == null || studentId <= 0)
                return FAILED;
            
            Student student = studentService.getById(studentId);
            if (student == null)
                return FAILED;
            
            studentService.deleteStudent(student);
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        return FAILED;
    } 
    
    @RequestMapping(value = "/searchStudent", method = RequestMethod.GET)
    public @ResponseBody Student[] searchStudent(@RequestParam String adminName, String name, 
            String schoolId, String professionalId, String telePhone, 
            String requirement, String grade, String status, String province)
    {
        Student[] students = null;
        if (validateAdmin(adminName))
        {
            try
            {
                province = !TextUtil.isEmpty(schoolId) ? null : province;
                students = studentService.queryStudents(name, 
                        NumberUtil.parseInteger(schoolId), NumberUtil.parseInteger(professionalId), 
                        telePhone, requirement, NumberUtil.parseInteger(grade), status, province);
            }
            catch (Exception e)
            {
                logger.error(LogUtil.toString(e));
                return null;
            }
        }
        return students;
    }
}
