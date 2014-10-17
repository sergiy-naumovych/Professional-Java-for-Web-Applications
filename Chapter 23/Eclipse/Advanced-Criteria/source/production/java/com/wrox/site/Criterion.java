package com.wrox.site;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class Criterion
{
    private final String propertyName;
    private final Operator operator;
    private final Object compareTo;

    public Criterion(String propertyName, Operator operator, Object compareTo)
    {
        this.propertyName = propertyName;
        this.operator = operator;
        this.compareTo = compareTo;
    }

    public String getPropertyName()
    {
        return this.propertyName;
    }

    public Operator getOperator()
    {
        return this.operator;
    }

    public Object getCompareTo()
    {
        return this.compareTo;
    }

    public static enum Operator
    {
        EQ {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.equal(r.get(c.getPropertyName()), c.getCompareTo());
            }
        },
        NEQ {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.notEqual(r.get(c.getPropertyName()), c.getCompareTo());
            }
        },
        LT {
            @Override @SuppressWarnings("unchecked")
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.lessThan(
                        r.<Comparable>get(c.getPropertyName()), getComparable(c)
                );
            }
        },
        LTE {
            @Override @SuppressWarnings("unchecked")
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.lessThanOrEqualTo(
                        r.<Comparable>get(c.getPropertyName()), getComparable(c)
                );
            }
        },
        GT {
            @Override @SuppressWarnings("unchecked")
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.greaterThan(
                        r.<Comparable>get(c.getPropertyName()), getComparable(c)
                );
            }
        },
        GTE {
            @Override @SuppressWarnings("unchecked")
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.greaterThanOrEqualTo(
                        r.<Comparable>get(c.getPropertyName()), getComparable(c)
                );
            }
        },
        LIKE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.like(
                        r.get(c.getPropertyName()), getString(c)
                );
            }
        },
        NOT_LIKE {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return b.notLike(
                        r.get(c.getPropertyName()), getString(c)
                );
            }
        },
        IN {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                Object o = c.getCompareTo();
                if(o == null)
                    return r.get(c.getPropertyName()).in();
                if(o instanceof Collection)
                    return r.get(c.getPropertyName()).in((Collection) o);
                throw new IllegalArgumentException(c.getPropertyName());
            }
        },
        NOT_IN {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                Object o = c.getCompareTo();
                if(o == null)
                    return b.not(r.get(c.getPropertyName()).in());
                if(o instanceof Collection)
                    return b.not(r.get(c.getPropertyName()).in((Collection) o));
                throw new IllegalArgumentException(c.getPropertyName());
            }
        },
        NULL {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return r.get(c.getPropertyName()).isNull();
            }
        },
        NOT_NULL {
            @Override
            public Predicate toPredicate(Criterion c, Root<?>r, CriteriaBuilder b)
            {
                return r.get(c.getPropertyName()).isNotNull();
            }
        };

        public abstract Predicate toPredicate(Criterion c, Root<?> r,
                                              CriteriaBuilder b);

        @SuppressWarnings("unchecked")
        private static Comparable<?> getComparable(Criterion c)
        {
            Object o = c.getCompareTo();
            if(o != null && !(o instanceof Comparable))
                throw new IllegalArgumentException(c.getPropertyName());
            return (Comparable<?>)o;
        }

        private static String getString(Criterion c)
        {
            if(!(c.getCompareTo() instanceof String))
                throw new IllegalArgumentException(c.getPropertyName());
            return (String)c.getCompareTo();
        }
    }
}
