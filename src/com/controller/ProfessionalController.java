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

import com.content.Professional;
import com.controller.util.ControllerUtil;
import com.dao.ProfessionalService;
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
@RequestMapping("/professional")
public class ProfessionalController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(ProfessionalController.class);
    ProfessionalService professionalService = ServiceFactory.getProfessionalService();
    @RequestMapping(value = "/getProfessional", method = RequestMethod.GET)
    public @ResponseBody Professional getProfessional(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return professionalService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateProfessional", method = RequestMethod.POST)
    public @ResponseBody String updateProfessional(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Professional professional = mapper.readValue(jsonString, Professional.class);
            if (TextUtil.isEmpty(professional.getCode()))
                return FAILED;
            
            Professional professionaldb = professionalService.getProfessionalByCode(professional.getCode());
            
            if (professionaldb != null 
                    && professionaldb.getId() != null 
                    && professionaldb.getId() > 0)
            {
                professionaldb.setName(professional.getName());
                professionaldb.setCode(professional.getCode());
                professionaldb.setUpdateDate(new Date());
                professionalService.updateProfessional(professionaldb);
            }
            else
            {
                professional.setUpdateDate(new Date());
                professionalService.createProfessional(professional);
            }
                
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/getAllProfessional", method = RequestMethod.GET)
    public @ResponseBody Professional[] getAllProfessional()
    {
        return professionalService.getAllProfessional();
    }
    
    @RequestMapping(value = "/deleteProfessional", method = RequestMethod.DELETE)
    public @ResponseBody String deleteProfessional(@RequestParam String id)
    {
        try
        {
            Integer idNow = NumberUtil.parseInteger(id);
            if (idNow == null)
                return FAILED;
            
            Professional professional = professionalService.getById(idNow);
            if (professional != null)
                professionalService.deleteProfessional(professional);
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
            return FAILED;
        }
        
        return SUCCESS;
    }
}
