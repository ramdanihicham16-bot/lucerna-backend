package com.lucerna.repository;

import com.lucerna.model.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long> {
    Optional<Finca> findByPolygonId(String polygonId);
}