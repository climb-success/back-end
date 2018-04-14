/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Group;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface GroupService
{
    public Group createGroup(Group group);
    
    public Group updateGroup(Group group);
    
    public Group getById(Integer id);
    
    public void deleteGroup(Group group);
    
    public Group[] queryGroups(String province, Integer schoolId, Integer professionalId, Integer year, String name);

    public Group queryGroupByName(String name);
}
