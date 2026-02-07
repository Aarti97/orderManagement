package com.example.ordermanagement.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.example.ordermanagement.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	 List<Order> findByOrderDateAndStatus_StatusId(
	            LocalDate orderDate,
	            Long statusId
	    );

	    // 🔹 PROCESS / COMPLETE → assigned user → TODAY
	    List<Order> findByOrderDateAndStatus_StatusIdAndAssignedUser_Username(
	            LocalDate orderDate,
	            Long statusId,
	            String username
	    );
	    
	    @Query("""
	    		   SELECT o FROM Order o
	    		   WHERE DATE(o.orderDate) = CURRENT_DATE
	    		""")
	    		List<Order> findTodayOrders();

//    // Orders by status + assigned user
//    List<Order> findByStatus_StatusIdAndAssignedUser_Username(
//            Long statusId, String username
//    );
}
//@Query("""
//        SELECT o FROM Order o
//        WHERE o.status.statusId = 1
//        AND o.orderDate = CURRENT_DATE
//    """)
//    List<Order> getTodayPendingOrders();
//
//    @Query("""
//        SELECT o FROM Order o
//        WHERE o.assignedUser.id = :userId
//        AND o.status.statusId = :status
//        AND o.orderDate = CURRENT_DATE
//    """)
//    List<Order> getTodayUserOrders(Integer userId, Integer status);
//
//    @Query("""
//        SELECT o FROM Order o
//        WHERE o.status.statusId != 1
//        ORDER BY o.orderDate DESC
//    """)
//    List<Order> getOrderHistory();