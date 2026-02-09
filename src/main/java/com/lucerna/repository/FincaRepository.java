package com.lucerna.repository;

import com.lucerna.model.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {
}