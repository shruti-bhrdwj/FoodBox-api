package com.foodboxapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodboxapi.models.Product;

public interface ProductRepo extends JpaRepository<Product,Integer>{
	

}

