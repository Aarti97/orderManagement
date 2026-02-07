

package com.example.ordermanagement.service;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.example.ordermanagement.entity.Customer;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.entity.Status;
import com.example.ordermanagement.entity.User;
import com.example.ordermanagement.repository.OrderRepository;
import com.example.ordermanagement.repository.StatusRepository;
import com.example.ordermanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentModeService paymentModeService;

    @Autowired
    private PaymentStatusService paymentStatusService;
    
    public List<Order> all() {
        return orderRepository.findAll();
    }

    /* ================= GET ALL TODAY'S PENDING ORDERS ================= */
//    public List<Order> getTodayPendingOrders() {
//        LocalDate today = LocalDate.now();
//        return orderRepository.findByOrderDateAndStatus_StatusId(today, 1L); // PENDING = 1
//    }

    /* ================= GET ORDER BY ID ================= */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /* ================= SAVE OR UPDATE ORDER ================= */
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /* ================= GET ORDER HISTORY ================= */
//    public List<Order> getOrderHistory() {
//        return orderRepository.findAllByOrderByOrderDateDesc();
//    }

    /* ================= DELETE ORDER ================= */
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
    public List<Order> getTodayOrders() {
        return orderRepository.findTodayOrders();
    }
    
    
//    @Transactional
//    public Order changeOrderStatus(Long orderId) {
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // 🔧 FIX 1: Integer → Long conversion
//        Long currentStatusId = order.getStatus().getStatusId().longValue();
//        Long nextStatusId;
//
//        if (currentStatusId.equals(OrderStatusConst.PENDING)) {
//            nextStatusId = OrderStatusConst.PROCESS;
//        }
//        else if (currentStatusId.equals(OrderStatusConst.PROCESS)) {
//            nextStatusId = OrderStatusConst.COMPLETE;
//        }
//        else {
//            throw new IllegalStateException("Order already completed");
//        }
//
//        Status nextStatus = statusRepository.findById(nextStatusId)
//                .orElseThrow(() -> new RuntimeException("Status not found"));
//
//        order.setStatus(nextStatus);
//
//        // 🔐 FIX 2: Get logged-in username from SecurityContext
//        String username = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 👤 Assign user ONLY when order goes to PROCESS
//        if (nextStatusId.equals(OrderStatusConst.PROCESS)) {
//            order.setAssignedUser(user);
//        }
//
//        return orderRepository.save(order);
//    }
    
    @Transactional
    public Order changeOrderStatus(Long orderId,
                                   Long paymentModeId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Long currentStatus = order.getStatus().getStatusId();

        // PENDING → PROCESS
        if (currentStatus == 1) {
            order.setStatus(statusRepository.getReferenceById(2L));

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            order.setAssignedUser(user);
        }

        // PROCESS → COMPLETE
        else if (currentStatus == 2) {

            if (paymentModeId == null) {
                throw new IllegalArgumentException(
                    "paymentModeId and paymentStatusId are required"
                );
            }

            order.setStatus(statusRepository.getReferenceById(3L));
            order.setPaymentMode(paymentModeService.getById(paymentModeId));
           
        }

        else {
            throw new IllegalStateException("Order already completed");
        }

        return orderRepository.save(order);
    }
    public List<Order> getOrdersByStatus(Long statusId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        LocalDate today = LocalDate.now();

        // 🔹 PENDING → ALL USERS → TODAY
        if (statusId.equals(1L)) {
            return orderRepository.findByOrderDateAndStatus_StatusId(
                    today,
                    1L
            );
        }

        // 🔹 PROCESS + COMPLETE → ONLY ASSIGNED USER → TODAY
        return orderRepository.findByOrderDateAndStatus_StatusIdAndAssignedUser_Username(
                today,
                statusId,
                username
        );
    }


   // public List<Order> getOrdersByStatusForUser(Long statusId) {
//
//        String username = SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getName();
//
//        // 🔹 PENDING → show ALL
//        if (statusId == 1) {
//        	 LocalDate today = LocalDate.now();
//             return orderRepository.findByOrderDateAndStatus_StatusId(today, 1L); // PENDING = 1
//        }
    
    
//
//        // 🔹 PROCESS & COMPLETE → only logged-in user
//        return orderRepository.findByStatus_StatusIdAndAssignedUser_Username(
//                statusId, username
//        );
//    }

  
}
