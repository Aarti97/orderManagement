package com.example.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.entity.PaymentMode;
import com.example.ordermanagement.service.PaymentModeService;

@RestController
@RequestMapping("/api/payment-modes")
@CrossOrigin("*")
public class PaymentModeController {

    @Autowired
    private PaymentModeService service;

    @GetMapping
    public List<PaymentMode> getPaymentModes() {
        return service.getAll();
    }
}
