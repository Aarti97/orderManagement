package com.example.ordermanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.repository.CustomerRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    /* ===== SAVE ===== */
    public Customer save(Customer c) {
        return repo.save(c);
    }

    /* ===== ALL ===== */
    public List<Customer> all() {
        return repo.findAll();
    }

    public Customer getByCustId(String custId) {
        return repo.findByCustId(custId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found with custId: " + custId));
    }

    
    public List<Customer> search(String keyword) {
        return repo
            .findBySocietyContainingIgnoreCaseOrPhoneNoContaining(
                keyword, keyword);
    }
    
}
