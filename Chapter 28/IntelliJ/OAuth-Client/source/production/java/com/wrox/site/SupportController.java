package com.wrox.site;

import com.wrox.config.annotation.WebController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.Map;

@WebController
public class SupportController
{
    private static final Logger log = LogManager.getLogger();

    @Inject @Qualifier("customerSupportRestTemplate")
    RestTemplate webService;

    @RequestMapping("support")
    public String getTickets(Map<String, Object> model)
    {
        try
        {
            TicketWebServiceList list = this.webService.getForObject(
                    "http://localhost:8080/support/services/Rest/ticket",
                    TicketWebServiceList.class
            );
            model.put("tickets", list);
        }
        catch(HttpStatusCodeException e)
        {
            log.error("HTTP Client Error: [{}] [{}], header [{}], body [{}].",
                    e.getStatusCode(), e.getStatusText(),
                    e.getResponseHeaders(), e.getResponseBodyAsString(), e);
            throw e;
        }
        catch(UserRedirectRequiredException e)
        {
            throw e;
        }
        catch(Exception ex)
        {
            if(ex.getCause() instanceof HttpStatusCodeException)
            {
                HttpStatusCodeException e =
                        (HttpStatusCodeException)ex.getCause();
                log.error("HTTP Client Error: [{}] [{}], header [{}], body [{}].",
                        e.getStatusCode(), e.getStatusText(),
                        e.getResponseHeaders(), e.getResponseBodyAsString(), e);
            }
            else
                log.error("HTTP Client Error", ex);
            throw ex;
        }

        return "support";
    }
}
