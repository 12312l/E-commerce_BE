package com.example.identity_service.repository;

import com.example.identity_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_UserId(Long userId);

    Optional<Order> findByUser_UserIdAndOrderId(Long userId, Long orderId);


//    @Query("""
//SELECT DISTINCT o FROM Order o
//JOIN FETCH o.orderDetails od
//JOIN FETCH od.productVariant pv
//JOIN FETCH pv.product p
//WHERE o.user.userId = :userId
//""")
//    List<Order> findAllByUserWithFullDetails(@Param("userId") Long userId);





}
