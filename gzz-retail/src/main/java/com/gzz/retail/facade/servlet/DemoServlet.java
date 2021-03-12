package com.gzz.retail.facade.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet demo
 * 纯servlet
 */
@WebServlet(urlPatterns = {"/servlet/demo", "/servlet/h"},
  initParams = {
    @WebInitParam(name = "username", value = "tom"),
    @WebInitParam(name = "encode", value = "utf-8")
})
public class DemoServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("helloWorld");
        //获取初始化参数
        ServletConfig config = this.getServletConfig();
        //获取username
        String username = config.getInitParameter("username");
        System.out.println(username);
        //获取encode
        String encode = config.getInitParameter("encode");
        System.out.println(encode);

        this.doPost(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("33333333333333333333333333");
        response.getWriter().println("2222222222222222222222222");
        response.getWriter().flush();
        response.getWriter().close();

    }
}
