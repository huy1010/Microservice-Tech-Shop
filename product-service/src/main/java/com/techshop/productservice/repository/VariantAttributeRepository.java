package com.techshop.productservice.repository;

import com.techshop.productservice.entity.VariantAttribute;
import com.techshop.productservice.key.VariantAttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantAttributeRepository extends JpaRepository<VariantAttribute, VariantAttributeKey> {

}
