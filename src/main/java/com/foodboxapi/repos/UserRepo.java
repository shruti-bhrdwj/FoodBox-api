package com.foodboxapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodboxapi.models.User;

public interface UserRepo extends JpaRepository<User,Integer>{

}
