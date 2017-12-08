/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.controller;

import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.content.Storage;
import com.controller.util.ControllerUtil;
import com.dao.StorageService;
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
@RequestMapping("/storage")
public class StorageController extends ControllerUtil 
{
    private static Logger logger = Logger.getLogger(StorageController.class);
    StorageService storageService = ServiceFactory.getStorageService();
    @RequestMapping(value = "/getStorage", method = RequestMethod.GET)
    public @ResponseBody Storage getStorage(@RequestParam String id)
    {
        Integer idNow = NumberUtil.parseInteger(id);
        if (idNow == null)
            return null;
        return storageService.getById(idNow);
    }
    
    @RequestMapping(value = "/updateStorage", method = RequestMethod.POST)
    public @ResponseBody String updateStorage(@RequestBody String jsonString)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Storage storage = mapper.readValue(jsonString, Storage.class);
            storage.setUpdateDate(new Date());
            if (storage.getId() == 0)
                storageService.createStorage(storage);
            else
                storageService.updateStorage(storage);
            
            File file = new File(storage.getLocation());
            if (!file.getParentFile().exists())
                file.mkdirs();
            if (!file.exists())
                file.mkdir();
            
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
    
    @RequestMapping(value = "/getAllStorage", method = RequestMethod.GET)
    public @ResponseBody Storage[] getAllStorage()
    {
        return storageService.getAllStorage();
    }
    
    @RequestMapping(value = "/deleteStorage", method = RequestMethod.DELETE)
    public @ResponseBody String deleteStorage(@RequestParam String id)
    {
        try
        {
            Integer idNow = NumberUtil.parseInteger(id);
            if (idNow == null)
                return FAILED;
            
            Storage Storage = storageService.getById(idNow);
            if (Storage != null)
                storageService.deleteStorage(Storage);
            return SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return FAILED;
    }
}
