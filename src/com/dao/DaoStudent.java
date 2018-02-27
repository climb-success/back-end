/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.content.Administrator;
import com.content.Config;
import com.content.Mail;
import com.content.Professional;
import com.content.School;
import com.content.Student;
import com.hibernate.DaoService;
import com.service.ServiceFactory;
import com.util.LogUtil;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoStudent extends DaoService implements StudentService
{
    private static Logger logger = Logger.getLogger(DaoStudent.class);
    
    public Student createStudent(Student student)
    {
        return (Student) create(student);
    }
    
    public Student updateStudent(Student student)
    {
        return (Student) update(student);
    }
    
    public Student getById(Integer id)
    {
        return (Student) find(Student.class, id);
    }
    
    public void deleteStudent(Student student)
    {
        delete(student);
    }
    
    public Student[] queryStudents(String name, String school, String professional, String requirement, Integer grade,String status)
    {
        Student[] students = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        args.put("school", TextUtil.isEmpty(school) ? null : "%" + school + "%");
        args.put("professional", TextUtil.isEmpty(professional) ? null : "%" + professional + "%");
        args.put("requirement", TextUtil.isEmpty(requirement) ? null : "%" + requirement + "%");
        args.put("grade", grade == null ? null : grade);
        args.put("status", TextUtil.isEmpty(status) ? null : "%" + status + "%");
        Object[] result = query("STUDENT.QUERY_BY_NAME_SCHOOL_PROFESSION_REQUIREMENT_GRADE_STATUS", args);
        if (result == null)
            return null;
        
        students = new Student[result.length];
        for (int i = 0; i < result.length; i ++)
            students[i] = (Student) result[i];
        
        return students;
    }

    /**
     * @see com.dao.StudentService#queryStudentsByTelePhone(java.lang.String)
     */
    @Override
    public Student queryStudentByTelePhone(String telePhone) 
    {
        Map args = new HashMap<>();
        args.put("telePhone", telePhone);
        Object[] result = query("STUDENT.QUERY_BY_TELEPHONE", args);
        if (result == null || result.length == 0)
            return null;
        
        return (Student) result[0];
    }

    /**
     * @see com.dao.StudentService#sendStudentEmail(com.content.Student)
     */
    @Override
    public boolean sendStudentEmail(Student student)
    {
        Administrator[] administrators = ServiceFactory.getAdminService().getAllAdmin();
        if (administrators == null || administrators.length == 0)
            return false;
        
        if (Student.STATUS_NOT_FINISH.equals(student.getStatus()))
        {
            EmailService emailService = ServiceFactory.getEmailService();
            String subject = "Ñ§Éú¶©µ¥";
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", student.getName());
            Config config = ServiceFactory.getConfigService().queryOneConfigByName(Config.POST_URL);
            String path = null;
            if (config != null 
                    && !TextUtil.isEmpty(config.getInput()))
                path = config.getInput();
            
            if (TextUtil.isEmpty(path))
                return false;
            
            path = path.endsWith("/") ? path : path + "/";
            path = path + "/student/adminStudent?id=" + student.getId();
            map.put("url", path);
            School school = ServiceFactory.getSchoolService().getById(student.getSchoolId());
            map.put("school", school.getName());
            Professional professional = ServiceFactory.getProfessionalService().getById(student.getProfessionalId());
            map.put("professional", professional.getName());
            map.put("requirement", student.getRequirement());
            map.put("telePhone", student.getTelePhone());
            map.put("qq", student.getQq());
            map.put("weixin", student.getWeixin());
            map.put("status", student.getStatus());
            String content = emailService.getContent(Mail.STUDENT_TEMPLATE, map);
            String to = null;
            List<String> ccList = new ArrayList<String>();
            for (Administrator admin : administrators)
            {
                if (TextUtil.isEmpty(admin.getEmail()))
                    continue;
                
                if (TextUtil.isEmpty(to))
                    to = admin.getEmail();
                else 
                    ccList.add(admin.getEmail());
            }
            
            try
            {
                return emailService.sendMail(to, ccList.toArray(new String[ccList.size()]), subject, content);
            }
            catch (Exception e)
            {
                logger.error(LogUtil.toString(e));
            }
        }
        return false;
    }
}
