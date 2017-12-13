/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Config;
import com.hibernate.DaoService;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoConfig extends DaoService implements ConfigService
{
    public Config createConfig(Config config)
    {
        return (Config) create(config);
    }
    
    public Config updateConfig(Config config)
    {
        return (Config) update(config);
    }
    
    public Config getById(Integer id)
    {
        return (Config) find(Config.class, id);
    }
    
    public void deleteConfig(Config config)
    {
        delete(config);
    }
    
    public Config[] queryConfigAll()
    {
        Config[] configs = null;
        Map args = new HashMap<>();
        Object[] result = query("CONFIG.QUERY_ALL", args);
        if (result == null)
            return new Config[0];
        
        configs = new Config[result.length];
        for (int i = 0; i < result.length; i ++)
            configs[i] = (Config) result[i];
        
        return configs;
    }

    @Override
    public Config[] queryConfigByName(String name)
    {
        Config[] configs = null;
        Map args = new HashMap<>();
        args.put("name", name);
        Object[] result = query("CONFIG.QUERY_BY_NAME", args);
        configs = new Config[result.length];
        for (int i = 0; i < result.length; i ++)
            configs[i] = (Config) result[i];
        
        return configs;
    }
    
    public Config queryOneConfigByName(String name)
    {
        Config[] configs = queryConfigByName(name);
        if (configs != null && configs.length > 0)
            return configs[0];
        
        return null;
    }
}
