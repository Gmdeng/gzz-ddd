package com.gzz.retail.facade.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *  异步执行
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/servlet/AsyncServlet" })
public class AsyncServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public AsyncServlet() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long t1 = System.currentTimeMillis();
        // 开启异步
        AsyncContext asyncContext = request.startAsync();
        // 执行业务代码
        CompletableFuture.runAsync(() -> {
            try {
                doSomeThing(asyncContext,
                        asyncContext.getRequest(), asyncContext.getResponse());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("async use:" + (System.currentTimeMillis() - t1));
    }

    private void doSomeThing(AsyncContext asyncContext, ServletRequest servletRequest, ServletResponse servletResponse) throws InterruptedException, IOException {
        System.out.println(servletRequest.getProtocol());
        // 模拟耗时操作
        TimeUnit.SECONDS.sleep(5);
        servletResponse.getWriter().append("done");

        // 业务代码处理完毕, 通知结束
        asyncContext.complete();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
