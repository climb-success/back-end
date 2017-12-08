/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.content.InformationCategory;
import com.content.Storage;
import com.controller.InformationCategoryController;
import com.hibernate.DaoService;
import com.service.ServiceFactory;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoInformationCategory extends DaoService implements InformationCategoryService
{
    private static Logger logger = Logger.getLogger(DaoInformationCategory.class);
    public InformationCategory createInformationCategory(InformationCategory informationCategory)
    {
        return (InformationCategory) create(informationCategory);
    }
    
    public InformationCategory updateInformationCategory(InformationCategory informationCategory)
    {
        return (InformationCategory) update(informationCategory);
    }
    
    public InformationCategory getById(Integer id)
    {
        return (InformationCategory) find(InformationCategory.class, id);
    }
    
    public void deleteInformationCategory(InformationCategory informationCategory)
    {
        delete(informationCategory);
    }
    
    public InformationCategory[] queryInformationCategorys(String name)
    {
        InformationCategory[] informationCategorys = null;
        Map args = new HashMap<>();
        args.put("name", TextUtil.isEmpty(name) ? null : "%" + name + "%");
        Object[] result = query("INFORMATIONCATEGORY.QUERY_BY_NAME", args);
        if (result == null)
            return null;
        
        informationCategorys = new InformationCategory[result.length];
        for (int i = 0; i < result.length; i ++)
            informationCategorys[i] = (InformationCategory) result[i];
        
        return informationCategorys;
    }

    /**
     * @see com.dao.InformationCategoryService#createStorageFolder(com.content.InformationCategory)
     */
    @Override
    public void createStorageFolder(InformationCategory informationCategory)
    {
        Storage storage = ServiceFactory.getStorageService().getStorageByName(Storage.INFORMATION);
        if (storage == null 
                || TextUtil.isEmpty(storage.getLocation()))
            logger.error("Please config the valid storage: " + Storage.INFORMATION);
        
        File path = new File(storage.getLocation(), informationCategory.getName());
        if (!path.getParentFile().exists())
            path.mkdirs();
        if (!path.exists())
            path.mkdir();
    }
}
