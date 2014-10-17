package com.wrox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@WebServlet(
        name = "indexServlet",
        urlPatterns = "/index"
)
public class IndexServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String view = "hello";
        if(request.getParameter("dates") != null)
        {
            request.setAttribute("date", new Date());
            request.setAttribute("calendar", Calendar.getInstance());
            request.setAttribute("instant", Instant.now());
            view = "dates";
        }
        else if(request.getParameter("text") != null)
        {
            request.setAttribute("shortText", "This is short text.");
            request.setAttribute(
                    "longText",
                    "This is really long text that should get cut off at 32 chars."
            );
            view = "text";
        }

        request.getRequestDispatcher("/WEB-INF/jsp/view/" + view + ".jsp")
               .forward(request, response);
    }
}
