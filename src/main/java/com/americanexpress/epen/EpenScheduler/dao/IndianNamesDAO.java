package com.americanexpress.epen.EpenScheduler.dao;

import com.americanexpress.epen.EpenScheduler.model.IndianName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndianNamesDAO extends JpaRepository<IndianName, Integer> {
}
