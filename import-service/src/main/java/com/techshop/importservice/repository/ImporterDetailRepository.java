package com.techshop.importservice.repository;


import com.techshop.importservice.entity.ImporterDetail;
import com.techshop.importservice.entity.ImporterDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImporterDetailRepository extends JpaRepository<ImporterDetail, ImporterDetailPK> {
}
