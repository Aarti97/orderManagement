package com.example.ordermanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.ApiResponse;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /* ===== CREATE ===== */
    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> create(
            @RequestBody Customer customer) {

        Customer savedCustomer = service.save(customer);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Customer added successfully",
                        savedCustomer
                )
        );
    }

    /* ===== ALL ===== */
    @GetMapping
    public List<Customer> all() {
        return service.all();
    }

    /* ===== SEARCH ===== */
    @GetMapping("/search")
    public List<Customer> search(@RequestParam String q) {
        return service.search(q);
    }

    /* ===== GET BY custId ===== */
    @GetMapping("/{custId}")
    public Customer byCustId(@PathVariable String custId) {
        return service.getByCustId(custId);
    }
}
