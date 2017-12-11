/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.content.Group;
import com.hibernate.DaoService;
import com.util.TextUtil;

/**
 * DaoStudent.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 19, 2017$
 * @since 1.0
 */
public class DaoGroup extends DaoService implements GroupService
{
    public Group createGroup(Group group)
    {
        return (Group) create(group);
    }
    
    public Group updateGroup(Group group)
    {
        return (Group) update(group);
    }
    
    public Group getById(Integer id)
    {
        return (Group) find(Group.class, id);
    }
    
    public void deleteGroup(Group te)
    {
        delete(te);
    }
    
    public Group[] queryGroups(String province, Integer schoolId, Integer professionalId, Integer year)
    {
        Group[] groups = null;
        Map args = new HashMap<>();
        args.put("province", TextUtil.isEmpty(province) ? null : province);
        args.put("schoolId", schoolId == null || schoolId == 0 ? null : schoolId);
        args.put("professionalId", professionalId == null || professionalId == 0 ? null : professionalId);
        args.put("year", year == null || year == 0 ? null : year);
        Object[] result = query("SUPER_GROUP.QUERY_BY_SCHOOL_PROFESSION", args);
        if (result == null)
            return new Group[0];
        
        groups = new Group[result.length];
        for (int i = 0; i < result.length; i ++)
            groups[i] = (Group) result[i];
        
        return groups;
    }

    @Override
    public Group queryGroupByName(String name)
    {
        if (TextUtil.isEmpty(name))
            return null;
        
        Group group = null;
        Map args = new HashMap<>();
        args.put("name", name);
        Object[] result = query("SUPER_GROUP.QUERY_BY_NAME", args);
        if (result == null 
                || result.length == 0)
            return null;
        return (Group) result[0];
    }
}
