package com.wrox;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "asyncServlet", urlPatterns = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet
{
    private static volatile int ID = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        final int id;
        synchronized(AsyncServlet.class)
        {
            id = ID++;
        }
        long timeout = request.getParameter("timeout") == null ?
                10_000L : Long.parseLong(request.getParameter("timeout"));

        System.out.println("Entering AsyncServlet.doGet(). Request ID = " + id +
                ", isAsyncStarted = " + request.isAsyncStarted());

        final AsyncContext context = request.getParameter("unwrap") != null ?
                request.startAsync() : request.startAsync(request, response);
        context.setTimeout(timeout);

        System.out.println("Starting asynchronous thread. Request ID = " + id +
                ".");

        AsyncThread thread = new AsyncThread(id, context);
        context.start(thread::doWork);

        System.out.println("Leaving AsyncServlet.doGet(). Request ID = " + id +
                ", isAsyncStarted = " + request.isAsyncStarted());
    }

    private static class AsyncThread
    {
        private final int id;
        private final AsyncContext context;

        public AsyncThread(int id, AsyncContext context)
        {
            this.id = id;
            this.context = context;
        }

        public void doWork()
        {
            System.out.println("Asynchronous thread started. Request ID = " +
                    this.id + ".");

            try {
                Thread.sleep(5_000L);
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpServletRequest request =
                    (HttpServletRequest)this.context.getRequest();
            System.out.println("Done sleeping. Request ID = " + this.id +
                    ", URL = " + request.getRequestURL() + ".");

            this.context.dispatch("/WEB-INF/jsp/view/async.jsp");

            System.out.println("Asynchronous thread completed. Request ID = " +
                    this.id + ".");
        }
    }
}
