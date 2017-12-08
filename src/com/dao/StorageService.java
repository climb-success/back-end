/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Storage;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface StorageService
{
    public Storage createStorage(Storage storage);
    
    public Storage updateStorage(Storage storage);
    
    public Storage getById(Integer id);
    
    public void deleteStorage(Storage storage);

    public Storage[] getAllStorage();
    
    public Storage getStorageByName(String name);
}
