/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.content;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Student.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 16, 2017$
 * @since 1.0
 */
@JsonInclude(Include.NON_NULL)
public class Teacher
{
    private int id;
    private String name;
    private Integer schoolId;
    private Integer professionalId;
    private String telePhone;
    private String qq;
    private String weixin;
    private String requirement;
    private Integer grade;
    private Date updateDate;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getSchoolId()
    {
        return schoolId;
    }
    
    public void setSchoolId(Integer schoolId)
    {
        this.schoolId = schoolId;
    }
    
    public Integer getProfessionalId()
    {
        return professionalId;
    }
    
    public void setProfessionalId(Integer professionalId)
    {
        this.professionalId = professionalId;
    }
    
    public String getTelePhone()
    {
        return telePhone;
    }
    
    public void setTelePhone(String telePhone)
    {
        this.telePhone = telePhone;
    }
    
    public String getQq()
    {
        return qq;
    }
    
    public void setQq(String qq)
    {
        this.qq = qq;
    }
    
    public String getWeixin()
    {
        return weixin;
    }
    
    public void setWeixin(String weixin)
    {
        this.weixin = weixin;
    }
    
    public String getRequirement()
    {
        return requirement;
    }

    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }

    public Integer getGrade()
    {
        return grade;
    }

    public void setGrade(Integer grade)
    {
        this.grade = grade;
    }

    @JsonIgnore
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public String toString()
    {
        return "id: " + this.id + " name " + this.name + " telePhone " + this.telePhone;
    }
}
