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

import com.content.Config;
import com.controller.util.ControllerUtil;
import com.dao.ConfigService;
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
@RequestMapping("/config")
public class ConfigController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(ConfigController.class);
    ConfigService configService = ServiceFactory.getConfigService();
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    public @ResponseBody Config getConfig(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return configService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateConfig", method = RequestMethod.POST)
    public @ResponseBody String updateConfig(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Config config = mapper.readValue(jsonString, Config.class);
            Config configdb = null;
            if (config.getId() != 0 
                    && config.getId() != null)
                configdb = configService.getById(config.getId());
            
            if (configdb != null 
                    && configdb.getId() != null 
                    && configdb.getId() != 0)
            {
                configdb.setName(config.getName());
                configdb.setInput(config.getInput());
                configdb.setDescrption(config.getDescrption());
                configdb.setUpdateDate(new Date());
                configService.updateConfig(configdb);
            }
            else
            {
                config.setId(new Integer(0));
                config.setUpdateDate(new Date());
                configService.createConfig(config);
            }
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/searchConfig", method = RequestMethod.GET)
    public @ResponseBody Config[] searchConfig(String name)
    {
        Config[] configs = null;
        try
        {
            configs = configService.queryConfigByName(name);
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
            return null;
        }
        return configs;
    }
}
