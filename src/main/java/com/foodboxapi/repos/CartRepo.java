package com.foodboxapi.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.foodboxapi.models.Cart;
import com.foodboxapi.models.User;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	Page<Cart> findByUserContaining(User user, Pageable paging);

}
