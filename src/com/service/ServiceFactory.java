/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.service;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dao.AdminService;
import com.dao.InformationCategoryService;
import com.dao.InformationService;
import com.dao.ProfessionalService;
import com.dao.SchoolProfessionalService;
import com.dao.SchoolService;
import com.dao.StorageService;
import com.dao.StudentService;
import com.dao.TeacherService;
/**
 * ServiceFactory.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public class ServiceFactory
{
    public static AdminService getAdminService()
    {
        return (AdminService) getService("service.Admin");
    }
    
    public static StudentService getStudentService()
    {
        return (StudentService) getService("service.Student");
    }
    
    public static TeacherService getTeacherService()
    {
        return (TeacherService) getService("service.Teacher");
    }
    
    public static SchoolService getSchoolService()
    {
        return (SchoolService) getService("service.School");
    }
    
    public static ProfessionalService getProfessionalService()
    {
        return (ProfessionalService) getService("service.Professional");
    }
    
    public static SchoolProfessionalService getSchoolProfessionalService()
    {
        return (SchoolProfessionalService) getService("service.SchoolProfessional");
    }
    
    public static StorageService getStorageService()
    {
        return (StorageService) getService("service.Storage");
    }
    
    public static InformationCategoryService getInformationCategoryService()
    {
        return (InformationCategoryService) getService("service.InformationCategory");
    }
    
    public static InformationService getInformationService()
    {
        return (InformationService) getService("service.Information");
    }
    
    private static Object getService(String beanName)
    {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac.getBean(beanName);
    }
}
