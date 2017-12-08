/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Storage;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoStorage extends DaoService implements StorageService
{
    public Storage createStorage(Storage storage)
    {
        return (Storage) create(storage);
    }
    
    public Storage updateStorage(Storage storage)
    {
        return (Storage) update(storage);
    }
    
    public Storage getById(Integer id)
    {
        return (Storage) find(Storage.class, id);
    }
    
    public void deleteStorage(Storage storage)
    {
        delete(storage);
    }

    @Override
    public Storage[] getAllStorage()
    {
        Storage[] storages = null;
        Map args = new HashMap<>();
        Object[] result = query("STORAGE.QUERY_ALL", args);
        if (result == null)
            return new Storage[0];
        
        storages = new Storage[result.length];
        for (int i = 0; i < result.length; i ++)
            storages[i] = (Storage) result[i];
        
        return storages;
    }

    /**
     * @see com.dao.StorageService#getStorageByName(java.lang.String)
     */
    @Override
    public Storage getStorageByName(String name)
    {
        if (TextUtil.isEmpty(name))
            return new Storage();
        Storage storage = null;
        Map args = new HashMap<>();
        args.put("name", name);
        Object[] result = query("STORAGE.QUERY_BY_NAME", args);
        if (result == null)
            return new Storage();
        
        return (Storage) result[0];
    }
}
