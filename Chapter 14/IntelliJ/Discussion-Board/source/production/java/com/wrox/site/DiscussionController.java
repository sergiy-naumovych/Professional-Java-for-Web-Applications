package com.wrox.site;

import com.wrox.site.entity.Discussion;
import com.wrox.site.entity.Reply;
import com.wrox.site.form.ReplyForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.util.Map;

@Controller
@RequestMapping("discussion/{discussionId:\\d+}")
public class DiscussionController
{
    @Inject DiscussionService discussionService;
    @Inject ReplyService replyService;

    @RequestMapping(value = {"", "*"}, method = RequestMethod.GET)
    public String viewDiscussion(Map<String, Object> model,
                                 @PathVariable("discussionId") long id)
    {
        Discussion discussion = this.discussionService.getDiscussion(id);
        if(discussion != null)
        {
            model.put("discussion", discussion);
            model.put("replies", this.replyService.getRepliesForDiscussion(id));
            model.put("replyForm", new ReplyForm());
            return "discussion/view";
        }

        return "discussion/errorNoDiscussion";
    }

    @RequestMapping(value = "reply", method = RequestMethod.POST)
    public ModelAndView reply(ReplyForm form,
                              @PathVariable("discussionId") long id)
    {
        Discussion discussion = this.discussionService.getDiscussion(id);
        if(discussion != null)
        {
            Reply reply = new Reply();
            reply.setDiscussionId(id);
            reply.setUser(form.getUser());
            reply.setMessage(form.getMessage());
            this.replyService.saveReply(reply);

            return new ModelAndView(new RedirectView("/discussion/" + id + "/" +
                    discussion.getUriSafeSubject(), true, false));
        }

        return new ModelAndView("discussion/errorNoDiscussion");
    }
}
