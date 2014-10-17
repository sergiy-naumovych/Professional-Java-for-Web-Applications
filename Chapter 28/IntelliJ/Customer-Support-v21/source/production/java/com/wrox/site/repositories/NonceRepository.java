package com.wrox.site.repositories;

import com.wrox.site.entities.Nonce;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NonceRepository extends CrudRepository<Nonce, Long>
{
    Nonce getByValueAndTimestamp(String value, long timestamp);

    @Modifying
    @Query("DELETE FROM Nonce n WHERE n.timestamp < :timestamp")
    void deleteWhereTimestampLessThan(long timestamp);
}
