package com.wrox.site.repositories;

import com.wrox.site.entities.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements SearchableRepository<TicketEntity>
{
    @PersistenceContext EntityManager entityManager;

    @Override
    public Page<SearchResult<TicketEntity>> search(String query,
                                                   boolean useBooleanMode,
                                                   Pageable pageable)
    {
        String mode = useBooleanMode ?
                "IN BOOLEAN MODE" : "IN NATURAL LANGUAGE MODE";
        String matchTicket = "MATCH(t.Subject, t.Body) AGAINST(?1 " + mode + ")";
        String matchComment = "MATCH(c.Body) AGAINST(?1 " + mode + ")";

        long total = ((Number)this.entityManager.createNativeQuery(
                "SELECT COUNT(DISTINCT t.TicketId) FROM Ticket t " +
                        "LEFT OUTER JOIN TicketComment c ON c.TicketId = " +
                        "t.TicketId WHERE " + matchTicket + " OR " + matchComment
        ).setParameter(1, query).getSingleResult()).longValue();

        @SuppressWarnings("unchecked")
        List<Object[]> results = this.entityManager.createNativeQuery(
                "SELECT DISTINCT t.*, (" + matchTicket + " + " + matchComment +
                        ") AS _ft_scoreColumn " +
                        "FROM Ticket t LEFT OUTER JOIN TicketComment c " +
                        "ON c.TicketId = t.TicketId " +
                        "WHERE " + matchTicket + " OR " + matchComment + " " +
                        "ORDER BY _ft_scoreColumn DESC, TicketId DESC",
                "searchResultMapping.ticket"
        ).setParameter(1, query)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<SearchResult<TicketEntity>> list = new ArrayList<>();
        results.forEach(o -> list.add(
                new SearchResult<>((TicketEntity)o[0], (Double)o[1])
        ));

        return new PageImpl<>(list, pageable, total);
    }
}
