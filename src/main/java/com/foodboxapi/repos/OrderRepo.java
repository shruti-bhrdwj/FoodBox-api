package com.foodboxapi.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.foodboxapi.models.Order;
import com.foodboxapi.models.User;
public interface OrderRepo extends JpaRepository<Order,Integer>{

	Page<Order> findByUserContaining(User user, Pageable pageable);
}