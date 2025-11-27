package com.example.identity_service.repository;

import com.example.identity_service.entity.Order;
import com.example.identity_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_UserId(Long userId);
    Optional<Order> findByUser_UserIdAndOrderId(Long userId, Long orderId);

    @Query("""
        select od.productVariant.product as product, sum(od.quantity) as totalSold
        from OrderDetail od
        WHERE od.order.orderStatus = com.example.identity_service.enums.OrderStatus.DELIVERED
        group by od.productVariant.product
        order by sum(od.quantity) desc 
""")
    List<Product> findBestSellingProducts();

//    @Query("""
//SELECT DISTINCT o FROM Order o
//JOIN FETCH o.orderDetails od
//JOIN FETCH od.productVariant pv
//JOIN FETCH pv.product p
//WHERE o.user.userId = :userId
//""")
//    List<Order> findAllByUserWithFullDetails(@Param("userId") Long userId);





}
