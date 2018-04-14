/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.content.Information;
import com.content.InformationCategory;
import com.content.Storage;
import com.hibernate.DaoService;
import com.service.ServiceFactory;
import com.util.DateTimeUtil;
import com.util.TextUtil;

/**
 * DaoInformation.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoInformation extends DaoService implements InformationService
{
    private static Logger logger = Logger.getLogger(DaoInformation.class);
    public Information createInformation(Information information)
    {
        return (Information) create(information);
    }
    
    public Information updateInformation(Information information)
    {
        return (Information) update(information);
    }
    
    public Information getById(Integer id)
    {
        return (Information) find(Information.class, id);
    }
    
    public void deleteInformation(Information information)
    {
        delete(information);
    }
    
    public Information[] queryInformations(String name, Integer schoolId, Integer professionalId, Integer year, String province)
    {
        Information[] informations = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        args.put("schoolId", schoolId == null ? null : schoolId);
        args.put("professionalId", professionalId == null ? null : professionalId);
        args.put("year", year == null ? null : year);
        args.put("province", TextUtil.isEmpty(province) ? null : "%" + province + "%");
        Object[] result = query("INFORMATION.QUERY_BY_NAME_SCHOOL_PROFESSIONAL_YEAR", args);
        if (result == null)
            return null;
        
        informations = new Information[result.length];
        for (int i = 0; i < result.length; i ++)
            informations[i] = (Information) result[i];
        
        return informations;
    }

    /**
     * @see com.dao.InformationService#generateURL(java.lang.Integer, java.lang.Integer, java.lang.String, java.util.Date)
     */
    @Override
    public String generateURL(Integer id, Integer informationCategoryId,
            String Url, Date setDate)
    {
        if (!TextUtil.isEmpty(Url))
            return Url;
        
        if (setDate == null)
            return null;
        
        Storage storage = ServiceFactory.getStorageService().getStorageByName(Storage.INFORMATION);
        if (storage == null 
                || TextUtil.isEmpty(storage.getPath())
                || (!storage.getPath().startsWith("http://") && !storage.getPath().startsWith("https://")))
        {
            logger.error("Please config the valid storage: " + Storage.INFORMATION);
            return null;
        }
        
        InformationCategory ic = ServiceFactory.getInformationCategoryService().getById(informationCategoryId);
        if (ic == null 
                || TextUtil.isEmpty(ic.getName()))
        {
            logger.error("Please config the InformationCategory: " + informationCategoryId);
            return null;
        }
        
        String path = storage.getPath();
        path = path.endsWith("/") ? path : path.concat("/");
        path = path.concat(ic.getName()).concat("/");
        path = path.concat(DateTimeUtil.dateAppendFmt(setDate)).concat("/");
        path = path.concat(id.toString()).concat(".html");
        return path;
    }
}
