package com.wrox.site;

import java.util.ArrayList;
import java.util.List;

public interface SearchCriteria extends List<Criterion>
{
    public static class Builder
    {
        public static SearchCriteria create()
        {
            return new SearchCriteriaImpl();
        }

        private static class SearchCriteriaImpl extends ArrayList<Criterion>
                implements SearchCriteria
        {

        }
    }
}
