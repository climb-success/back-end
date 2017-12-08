/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package test;

import com.content.Student;
import com.hibernate.DaoService;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import junit.framework.TestCase;

/**
 * TestHibnerate.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public class TestHibnerate  extends TestCase
{
    private DaoService daoService = new DaoService();
    public void testInsert()
    {
        Student student = new Student();
        student.setName("333");
        student.setProfessionalId(1);
        student.setQq("1243354");
        student.setSchoolId(1);
        student.setTelePhone("1235346567");
        student.setWeixin("weeewewe");
        
        daoService.create(student);
    }
    
    public void testSelect()
    {
        Student student = (Student) daoService.find(Student.class, new Integer(2));
        student.getId();
        student.getName();
    }
    
    public void testQuery()
    {
        String conditon = "we";
        Map args = new HashedMap();
        args.put("name", "%" + conditon + "%");
        Object[] students = daoService.query("STUDENT.QUERY_BY_SCHOOL", args);
        for (Object student : students)
        {
            System.out.println(((Student) student).toString());
        }
    }
}
