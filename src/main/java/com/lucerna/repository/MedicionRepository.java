package com.lucerna.repository;

import com.lucerna.model.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
}
