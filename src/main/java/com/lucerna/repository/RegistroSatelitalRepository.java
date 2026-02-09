package com.lucerna.repository;

import com.lucerna.model.RegistroSatelital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroSatelitalRepository extends JpaRepository<RegistroSatelital, Long> {
}
