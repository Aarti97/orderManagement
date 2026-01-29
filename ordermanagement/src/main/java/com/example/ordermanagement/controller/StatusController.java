package com.example.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.entity.Status;
import com.example.ordermanagement.service.StatusService;

@RestController
@RequestMapping("/api/order-status")
@CrossOrigin("*")
public class StatusController {

    @Autowired
    private StatusService service;

    @GetMapping
    public List<Status> getOrderStatus() {
        return service.getAll();
    }
}