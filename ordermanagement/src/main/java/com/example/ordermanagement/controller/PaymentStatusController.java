package com.example.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.entity.PaymentStatus;
import com.example.ordermanagement.service.PaymentStatusService;

@RestController
@RequestMapping("/api/payment-status")
@CrossOrigin("*")
public class PaymentStatusController {

    @Autowired
    private PaymentStatusService service;

    @GetMapping
    public List<PaymentStatus> getPaymentStatus() {
        return service.getAll();
    }
}
