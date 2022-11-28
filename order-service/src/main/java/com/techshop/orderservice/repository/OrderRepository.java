package com.techshop.orderservice.repository;

import com.techshop.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByIsDeletedFalse();
    List<Order> findByUserId(Long userId);
    List<Order> findByCreatedAtBetweenOrderByCreatedAt(LocalDateTime createdAt, LocalDateTime createdAt2);
}
