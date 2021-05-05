package com.gzz.retail.infra.started;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TServletContextListener implements ServletContextListener {
    /**
     * 在初始化Web应用程序中的任何过滤器或servlet之前，将通知所有servletContextListener上下文初始化。
     * */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //ServletContext servletContext = sce.getServletContext();
        System.out.println("执行contextInitialized方法");
    }

}
