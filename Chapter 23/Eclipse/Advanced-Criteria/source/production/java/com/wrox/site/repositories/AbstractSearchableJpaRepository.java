package com.wrox.site.repositories;

import com.wrox.site.Criterion;
import com.wrox.site.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

abstract class AbstractSearchableJpaRepository<T>
        extends AbstractDomainClassAwareRepository<T>
        implements SearchableRepository<T>
{
    @PersistenceContext protected EntityManager entityManager;

    @Override
    public Page<T> search(SearchCriteria criteria, Pageable pageable)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<T> countRoot = countCriteria.from(this.domainClass);
        long total = this.entityManager.createQuery(
                countCriteria.select(builder.count(countRoot))
                        .where(toPredicates(criteria, countRoot, builder))
        ).getSingleResult();

        CriteriaQuery<T> pageCriteria = builder.createQuery(this.domainClass);
        Root<T> pageRoot = pageCriteria.from(this.domainClass);
        List<T> list = this.entityManager.createQuery(
                pageCriteria.select(pageRoot)
                        .where(toPredicates(criteria, pageRoot, builder))
                        .orderBy(toOrders(pageable.getSort(), pageRoot, builder))
        ).setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(new ArrayList<>(list), pageable, total);
    }

    private static Predicate[] toPredicates(SearchCriteria criteria, Root<?> root,
                                            CriteriaBuilder builder)
    {
        Predicate[] predicates = new Predicate[criteria.size()];
        int i = 0;
        for(Criterion c : criteria)
            predicates[i++] = c.getOperator().toPredicate(c, root, builder);
        return predicates;
    }
}
