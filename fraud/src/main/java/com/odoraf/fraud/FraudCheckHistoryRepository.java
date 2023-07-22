package com.odoraf.fraud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface FraudCheckHistoryRepository
        extends JpaRepository<FraudCheckHistory, Integer> {

    FraudCheckHistory findByEmail(String customerEmail);

    boolean existsByEmail(String customerEmail);


}
