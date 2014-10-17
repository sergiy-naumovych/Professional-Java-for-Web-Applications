package com.wrox.site;

import com.wrox.site.entity.Reply;

import java.util.List;

public interface ReplyService
{
    List<Reply> getRepliesForDiscussion(long discussionId);
    void saveReply(Reply reply);
}
