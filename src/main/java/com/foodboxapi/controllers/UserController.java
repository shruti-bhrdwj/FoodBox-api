package com.foodboxapi.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodboxapi.models.Role;
import com.foodboxapi.models.User;
import com.foodboxapi.models.UserRole;
import com.foodboxapi.services.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService uService;
	
	//creating user
	@PostMapping("/signup")
	public ResponseEntity<User> createUser(@RequestBody User newuser) throws Exception {
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("USER");
		
		UserRole usrole = new UserRole();
		usrole.setRole(role);
		
		Set<UserRole> usrSet = new HashSet<>();
		usrSet.add(usrole);
		User user=  this.uService.createUser(newuser, usrSet);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
		
	}
	
	//user login authorization
	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity<HashMap> userLogin(@RequestParam String username, @RequestParam String password) {
		
		HashMap<String, Object> response = new HashMap<>();
		         
		if(uService.userAuth(username, password)){
			User user = this.uService.findByUsername(username);
			response.put("data", user);
			response.put("status", 200);
			return new ResponseEntity<>(response,HttpStatus.OK);
					
		} else {
			response.put("User not found", "404");
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
		
	}
	
	//get user by username
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User user = this.uService.findByUsername(username);
		return new ResponseEntity<>(user,HttpStatus.FOUND);
	}
	
	//delete user by id
	@DeleteMapping("/{userid}")
	public ResponseEntity<String> delUser(@PathVariable("userid") int userId) {
		this.uService.delUserById(userId);
		return new ResponseEntity<>("User deleted successfully!",HttpStatus.OK);			
	}
	
	//update user
	@PostMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id,@RequestParam String password, @RequestParam String email,@RequestParam String username) {
		User user = this.uService.updateUserDetails(id,password,email,username);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	//list of users
	@GetMapping("/all")
	public  ResponseEntity<List<User>>  allusers(){
		List<User> users = this.uService.getAllUsers();
		return  new ResponseEntity<>(users,HttpStatus.OK);	
	}

}
