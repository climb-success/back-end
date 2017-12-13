/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.hibernate.DaoService;
import com.util.FileUtil;
import com.util.LogUtil;
import com.util.TextUtil;

/**
 * DaoEmail.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Dec 12, 2017$
 * @since 1.0
 */
public class DaoEmail extends DaoService implements EmailService
{
    private static Logger logger = Logger.getLogger(DaoEmail.class);
    
    private JavaMailSenderImpl mailSender = createMailSender();
    private String userName;
    private String password;
    private String host;
    private int port;
    
    public static final String TEMPLATE_PATH = "META-INF/template/";

    /**
     * @see com.dao.EmailService#getContent(java.lang.String, java.util.Map)
     */
    @Override
    public String getContent(String templateName, Map<String, String> map)
    {
        String path = getTemplateResourcePath(templateName);
        if (TextUtil.isEmpty(path))
            return null;
        
        File file = new File(path);
        if (!file.exists())
            return null;
        
        String content = null;
        
        try
        {
            content =  FileUtil.getFileContent(path);
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        if (TextUtil.isEmpty(content))
            return null;
        
        if (map != null && !map.isEmpty())
        {
            for(Map.Entry<String, String> entry: map.entrySet())
            {
                content = content.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
            }
                
        }
        
        return content;
    }
    
    /**
     * @param template
     * @return
     */
    private String getTemplateResourcePath(String templateName)
    {
        return FileUtil.projectPath() + "/" + TEMPLATE_PATH + templateName;
    }

    public boolean sendMail(String to, String[] cc, String subject, String content)
    {
        return sendMail(to, cc, subject, content, null);
    }
    
    
    /**
     * 发送邮件
     *
     * @param to 接受人
     * @param subject 主题
     * @param content 发送内容
     * @param cc 抄送
     * @param attachs 附件
     */
    public boolean sendMail(String to, String[] cc, String subject, String content, String[] attachs)
    {
        try
        {
            mailSender = createMailSender();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(this.userName, "sss");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.setBcc(cc);
            if (attachs != null && attachs.length > 0)
            {
                for (String attach : attachs)
                {
                    File file = new File(attach);
                    if (file.exists())
                        messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
                }
            }
            
            mailSender.send(mimeMessage);
            return true;
        }
        catch (Exception e)
        {
            logger.error(LogUtil.toString(e));
        }
        
        return false;
    }
    
    private JavaMailSenderImpl createMailSender() 
    {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(this.host);
        sender.setPort(this.port);
        sender.setUsername(this.userName);
        sender.setPassword(this.password);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }
    
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }
}
