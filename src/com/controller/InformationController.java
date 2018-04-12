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

import com.content.Information;
import com.controller.util.ControllerUtil;
import com.dao.InformationService;
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
@RequestMapping("/information")
public class InformationController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(InformationController.class);
    InformationService informationService = ServiceFactory.getInformationService();
    @RequestMapping(value = "/getInformation", method = RequestMethod.GET)
    public @ResponseBody Information getInformation(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return informationService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
    public @ResponseBody String updateInformation(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Information information = mapper.readValue(jsonString, Information.class);
            information.setUpdateDate(new Date());
            
            Information informationdb = informationService.getById(information.getId());
            if (informationdb != null && information.getId() != 0)
            {
                informationdb.setName(information.getName());
                informationdb.setInformationCategoryId(information.getInformationCategoryId());
                informationdb.setSchoolId(information.getSchoolId());
                informationdb.setProfessionalId(information.getProfessionalId());
                informationdb.setUrl(information.getUrl());
                informationdb.setContent(information.getContent());
                informationdb.setYear(information.getYear());
                informationdb.setUpdateDate(information.getUpdateDate());
                informationService.updateInformation(informationdb);
            }
            else
            {
                information.setSetDate(new Date());
                informationService.createInformation(information);
            }
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/searchInformation", method = RequestMethod.GET)
    public @ResponseBody Information[] searchInformation(String name, String schoolId, String professionalId, String year)
    {
        Information[] informations = null;
        try
        {
            informations = informationService.queryInformations(name, NumberUtil.parseInteger(schoolId), 
                    NumberUtil.parseInteger(professionalId), NumberUtil.parseInteger(year));
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
            return new Information[0];
        }
        
        return informations;
    }
}
