/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Config;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface ConfigService
{
    public Config createConfig(Config config);
    
    public Config updateConfig(Config config);
    
    public Config getById(Integer id);
    
    public void deleteConfig(Config config);
    
    public Config[] queryConfigByName(String name);
    
    public Config queryOneConfigByName(String name);
}
