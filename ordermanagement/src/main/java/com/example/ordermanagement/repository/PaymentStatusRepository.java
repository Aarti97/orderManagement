package com.example.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.entity.Payment;
import com.example.ordermanagement.entity.PaymentStatus;
@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
	
}
