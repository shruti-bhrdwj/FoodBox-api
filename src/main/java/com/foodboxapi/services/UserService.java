package com.foodboxapi.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodboxapi.models.User;
import com.foodboxapi.models.UserRole;
import com.foodboxapi.repos.RoleRepo;
import com.foodboxapi.repos.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	//creating user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		User usr = this.findByUsername(user.getUsername());
		if(usr!=null) {
			System.out.println("user is already there!");
			throw new Exception("User already present!");
		}else {
			for(UserRole us :userRoles) {
				roleRepo.save(us.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			usr = this.userRepo.save(user);
		    return usr;
		}
	}

	//deleting user
	public void delUserById(int userId) {
		this.userRepo.deleteById(userId);
		
	}
	
	//updating user details
	public User updateUserDetails(int id,String pwd, String email, String uname) {
		User us = userRepo.findById(id).get();
				us.setUsername(uname);
				us.setPassword(pwd);
				us.setEmail(email);
				return us;
	}

	//user login
	public boolean userAuth(String username, String pwd) {
		List<User> users = userRepo.findAll();
		for(User us : users) {
			if(us.getUsername().equals(username) && us.getPassword().equals(pwd)) {
				return true;
			}
		}
		return false;
	}

	//getting all users
	public List<User> getAllUsers() {
	     return userRepo.findAll();
	}

	//get user by id
	public User findUserById(String userid) {
		int id = Integer.parseInt(userid);
		return userRepo.findById(id).get();
	}
	
//	public boolean changePassword(int id,String oldPassword, String newPassword) {
//		User us = userRepo.findById(id).get();
//		if (us.getUsername().equals(oldPassword)){
//			us.setPassword(newPassword);
//			userRepo.save(us);
//		}
//		if(us.getPassword().equals(newPassword)) return true; 
//		else return false;
//	\\

	//get user by username
	public User findByUsername(String username) {
		List<User> users = userRepo.findAll();
		for(User us : users) {
			if(us.getUsername().equals(username)) 
				return us;
	    }
		return null;
   }
 
	public boolean checkAdmin(String userName) {
		if(userName.equals("shruti")) return true;
		else return false;
	}
	
}
