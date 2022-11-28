package com.techshop.orderservice.repository;

import com.techshop.orderservice.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByIsDeletedFalse();

    @Query("FROM Voucher v WHERE v.voucherName = :name")
    Optional<Voucher> findByVoucherName(String name);

    boolean existsByVoucherName(String voucherName);
}
