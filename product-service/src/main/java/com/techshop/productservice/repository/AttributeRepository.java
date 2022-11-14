package com.techshop.productservice.repository;

import com.techshop.productservice.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
//    List<Attribute> findAttributeByCategoryId(Long categoryId);
}
