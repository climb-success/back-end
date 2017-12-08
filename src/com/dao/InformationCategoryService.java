/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.InformationCategory;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface InformationCategoryService
{
    public InformationCategory createInformationCategory(InformationCategory InformationCategory);
    
    public InformationCategory updateInformationCategory(InformationCategory InformationCategory);
    
    public InformationCategory getById(Integer id);
    
    public void deleteInformationCategory(InformationCategory InformationCategory);
    
    public InformationCategory[] queryInformationCategorys(String name);

    public void createStorageFolder(InformationCategory informationCategory);
}
