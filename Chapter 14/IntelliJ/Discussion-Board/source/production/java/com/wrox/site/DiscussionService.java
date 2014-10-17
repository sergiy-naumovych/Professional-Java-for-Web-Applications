package com.wrox.site;

import com.wrox.site.entity.Discussion;

import java.util.List;

public interface DiscussionService
{
    List<Discussion> getAllDiscussions();
    Discussion getDiscussion(long id);
    void saveDiscussion(Discussion discussion);
}
