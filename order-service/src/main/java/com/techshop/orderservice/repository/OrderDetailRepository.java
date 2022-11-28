package com.techshop.orderservice.repository;

import com.techshop.orderservice.entity.OrderDetail;
import com.techshop.orderservice.entity.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {
}
