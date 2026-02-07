package com.example.ordermanagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordermanagement.dto.CompleteOrderRequest;
import com.example.ordermanagement.dto.OrderRequest;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.service.CustomerService;
import com.example.ordermanagement.service.OrderService;
import com.example.ordermanagement.service.PaymentModeService;
import com.example.ordermanagement.service.PaymentStatusService;
import com.example.ordermanagement.service.StatusService;

@RestController
@RequestMapping("api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Autowired private CustomerService customerService;
    @Autowired private PaymentStatusService paymentStatusService;
    @Autowired private PaymentModeService paymentModeService;
    @Autowired private StatusService statusService;
    
    
    
    @GetMapping
    public List<Order> all() {
        return orderService.all();
    }
    
    @PostMapping
    public Order create(@RequestBody OrderRequest req) {

        Order order = new Order();
        order.setQuantity(req.getQuantity());
        order.setOrderDate(LocalDate.now());

        order.setCustomer(customerService.getByCustId(req.getCustomerId()));
        order.setStatus(statusService.getStatusById(1L)); // PENDING
//        order.setPaymentStatus(paymentStatusService.getById(1L));
        order.setPaymentMode(paymentModeService.getById(1L));

        return orderService.save(order);
    }
    

//    @PatchMapping("/{orderId}/status")
//    public ResponseEntity<Order> changeStatus(
//            @PathVariable Long orderId,
//            @RequestBody(required = false) Map<String, Long> body) {
//
//        Long paymentModeId = body != null ? body.get("paymentModeId") : null;
//        Long paymentStatusId = body != null ? body.get("paymentStatusId") : null;
//
//        Order updatedOrder =
//                orderService.changeOrderStatus(orderId, paymentModeId, paymentStatusId);
//
//        return ResponseEntity.ok(updatedOrder);
//    }
    
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody(required = false) CompleteOrderRequest req
    ) {

        Long paymentModeId = req != null ? req.getPaymentModeId() : null;
//        Long paymentStatusId = req != null ? req.getPaymentStatusId() : null;

        Order updatedOrder =
                orderService.changeOrderStatus(orderId, paymentModeId);

        return ResponseEntity.ok(updatedOrder);
    }
    
    @GetMapping("/status/{statusId}")
    public List<Order> getByStatus(@PathVariable Long statusId) {
        return orderService.getOrdersByStatus(statusId);
    }
    
    @GetMapping("/orders/today")
    public List<Order> getTodayOrders() {
        return orderService.getTodayOrders();
    }


}

