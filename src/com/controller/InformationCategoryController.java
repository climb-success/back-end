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

import com.content.InformationCategory;
import com.controller.util.ControllerUtil;
import com.dao.InformationCategoryService;
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
@RequestMapping("/informationCategory")
public class InformationCategoryController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(InformationCategoryController.class);
    InformationCategoryService informationCategoryService = ServiceFactory.getInformationCategoryService();
    @RequestMapping(value = "/getInformationCategory", method = RequestMethod.GET)
    public @ResponseBody InformationCategory getInformationCategory(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return informationCategoryService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateInformationCategory", method = RequestMethod.POST)
    public @ResponseBody String updateInformationCategory(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            InformationCategory informationCategory = mapper.readValue(jsonString, InformationCategory.class);
            informationCategory.setUpdateDate(new Date());
            if (informationCategory.getId() == 0)
                informationCategoryService.createInformationCategory(informationCategory);
            else
                informationCategoryService.updateInformationCategory(informationCategory);
            informationCategoryService.createStorageFolder(informationCategory);
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        return FAILED;
    }
    
    @RequestMapping(value = "/searchInformationCategory", method = RequestMethod.GET)
    public @ResponseBody InformationCategory[] searchInformationCategory(String name)
    {
        InformationCategory[] informationCategorys = null;
        try
        {
            informationCategorys = informationCategoryService.queryInformationCategorys(name);
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
            return null;
        }
        
        return informationCategorys;
    }
}
