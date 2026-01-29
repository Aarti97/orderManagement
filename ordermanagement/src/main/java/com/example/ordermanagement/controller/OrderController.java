package com.example.ordermanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.OrderRequest;
import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.repository.UserRepository;
import com.example.ordermanagement.service.*;
@RestController
@RequestMapping("/api/orders1")   // ✅ FIXED
@CrossOrigin("*")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private CustomerService customerService;
    @Autowired private PaymentStatusService paymentStatusService;
    @Autowired private PaymentModeService paymentModeService;
    @Autowired private StatusService statusService;

    /* ================= TEST ================= */
    @GetMapping("/test")
    public String test() {
        System.out.println("ORDER CONTROLLER HIT");
        return "OK";
    }

    /* ================= GET ALL ORDERS ================= */
    @GetMapping
    public List<Order> all() {
        return orderService.all();
    }

    /* ================= CREATE ORDER ================= */
    @PostMapping
    public Order create(@RequestBody OrderRequest req) {

        Order order = new Order();
        order.setQuantity(req.getQuantity());
        order.setOrderDate(LocalDate.now());

        order.setCustomer(customerService.getByCustId(req.getCustomerId()));
        order.setStatus(statusService.getStatusById(1L)); // PENDING
        order.setPaymentStatus(paymentStatusService.getById(1L));
        order.setPaymentMode(paymentModeService.getById(1L));

        return orderService.save(order);
    }

    /* ================= STATUS FLOW ================= */
//    @PatchMapping("/{orderId}/status")
//    public ResponseEntity<Order> changeOrderStatus(
//            @PathVariable("orderId") Long orderId) {
//
//        String username = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        System.out.println("LOGGED-IN USER: " + username);
//        System.out.println("🔥 CONTROLLER METHOD HIT 🔥");
//
//      //  Order updatedOrder = orderService.changeOrderStatus(orderId);
//        return ResponseEntity.ok(updatedOrder);
//    }

    /* ================= ORDER HISTORY ================= */
  

    /* ================= GET BY ID ================= */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
