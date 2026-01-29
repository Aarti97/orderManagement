package com.example.ordermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.PaymentStatus;
import com.example.ordermanagement.repository.PaymentStatusRepository;
@Service
public class PaymentStatusService {

    @Autowired
    private PaymentStatusRepository repository;

    public List<PaymentStatus> getAll() {
        return repository.findAll();
    }


    public PaymentStatus getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("PaymentStatus not found"));
    }
}