/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.content;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.ServiceFactory;
import com.util.TextUtil;

/**
 * New.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 28, 2017$
 * @since 1.0
 */
public class Information
{
    private Integer id;
    private String name;
    private Integer informationCategoryId;
    private Integer schoolId;
    private Integer professionalId;
    private String url;
    private String content;
    private Integer year;
    private Date setDate;
    private Date updateDate;
    
    private String realUrl;
    
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
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
    public Integer getInformationCategoryId()
    {
        return informationCategoryId;
    }
    public void setInformationCategoryId(Integer informationCategoryId)
    {
        this.informationCategoryId = informationCategoryId;
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
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public Integer getYear()
    {
        return year;
    }
    public void setYear(Integer year)
    {
        this.year = year;
    }
    @JsonIgnore
    public Date getSetDate()
    {
        return setDate;
    }
    public void setSetDate(Date setDate)
    {
        this.setDate = setDate;
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
    
    public String getRealUrl()
    {
        if (!TextUtil.isEmpty(realUrl))
            return realUrl;
        
        return ServiceFactory.getInformationService().generateURL(id, informationCategoryId, url, setDate);
    }
    
    public void setRealUrl(String realUrl)
    {
        this.realUrl = realUrl;
    }
}
