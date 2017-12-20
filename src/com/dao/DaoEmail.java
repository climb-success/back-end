/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

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
    
    private Session mailSession;
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
        //logger.info(file.getAbsoluteFile());
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
     * @param ccs 抄送
     * @param attachs 附件
     */
    public boolean sendMail(String to, String[] ccs, String subject, String content, String[] attachs)
    {
        
        mailSession = createMailSession();
        MimeMessage message = new MimeMessage(mailSession);
        
        try 
        {
            // 设置发件人
            InternetAddress from = new InternetAddress(this.userName);
            message.setFrom(from);
            Address[] a = new Address[1];
            a[0] = new InternetAddress(this.userName);
            message.setReplyTo(a);
            // 设置收件人
            InternetAddress toP = new InternetAddress(to);
            message.setRecipient(MimeMessage.RecipientType.TO, toP);
            
            if(ccs != null && ccs.length > 0)
            {
                for (String cc : ccs)
                    message.addRecipients(MimeMessage.RecipientType.CC, cc);
            }
            
            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");
            
            // 发送邮件
            Transport.send(message);
            return true;
        }
        catch (Exception e) 
        {
            logger.error(LogUtil.toString(e));
        }
        
        return false;
    }
    
    /**
     * @return
     */
    private Session createMailSession()
    {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", this.port);
        props.put("mail.smtp.port", this.port);
        // 发件人的账号
        props.put("mail.user", this.userName);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", this.password);
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        return Session.getInstance(props, authenticator);
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
