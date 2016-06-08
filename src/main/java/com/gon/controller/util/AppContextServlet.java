
package com.gon.controller.util;

import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AppContextServlet extends HttpServlet
{

    private static Logger logger = Logger.getLogger(AppContextServlet.class);
    private static final long serialVersionUID = 1059475193078914321L;
    private static WebApplicationContext webApplicationContext;
    private static Properties applicationProp;
    
    public AppContextServlet()
    {
    }

    private static synchronized void loadProperties()
    {
        if(applicationProp == null)
        {
            applicationProp = new Properties();
            try
            {
                applicationProp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            }
            catch(Exception e)
            {
                logger.error("", e);
            }
        }
    }

    public void init()
    {
        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

    public static Object getBean(String id)
    {
        return null == webApplicationContext ? null : webApplicationContext.getBean(id);
    }

    public static String getProperty(String key)
    {
        return getProperty(key, "");
    }

    public static String getProperty(String key, String value)
    {
        if(applicationProp == null)
            loadProperties();
        return applicationProp.getProperty(key, value);
    }

}