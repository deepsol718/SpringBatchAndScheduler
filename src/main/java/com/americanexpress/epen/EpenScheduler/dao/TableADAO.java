package com.americanexpress.epen.EpenScheduler.dao;

import com.americanexpress.epen.EpenScheduler.model.IndianName;
import com.americanexpress.epen.EpenScheduler.model.TableA;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TableADAO extends JpaRepository<TableA, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into epen.table_a values (1, :name)", nativeQuery = true)
    void updateData(String name);
}
