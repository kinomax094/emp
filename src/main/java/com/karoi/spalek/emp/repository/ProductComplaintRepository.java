package com.karoi.spalek.emp.repository;

import com.karoi.spalek.emp.model.ProductComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductComplaintRepository extends JpaRepository<ProductComplaint, Long> {

    Optional<ProductComplaint> findProductComplaintByIdentifier(String id);
}
