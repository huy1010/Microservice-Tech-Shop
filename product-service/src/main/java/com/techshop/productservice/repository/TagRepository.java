package com.techshop.productservice.repository;

import com.techshop.productservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.attribute.attributeId = :attributeId")
    Tag findTagByAttributeId(Long attributeId);
}
