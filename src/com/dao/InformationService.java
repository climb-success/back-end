/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.Date;

import com.content.Information;

/**
 * InformationService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface InformationService
{
    public Information createInformation(Information information);
    
    public Information updateInformation(Information information);
    
    public Information getById(Integer id);
    
    public void deleteInformation(Information information);
    
    public Information[] queryInformations(String name, Integer schoolId, Integer professionalId, Integer year);
    
    public String generateURL(Integer id, Integer informationCategoryId, String Url, Date setDate);
}
