package com.foodboxapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodboxapi.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	

}
