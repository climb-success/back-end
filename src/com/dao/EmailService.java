/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.util.Map;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface EmailService
{
    public boolean sendMail(String to, String[] cc, String subject, String content);
    
    public boolean sendMail(String to, String[] cc, String subject, String content, String[] attachs);

    /**
     * generate the email content by the template.
     * @param templateName
     * @param map
     * @return
     */
    public String getContent(String templateName, Map<String, String> map);
}
