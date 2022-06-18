package com.foodboxapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodboxapi.models.Order;
import com.foodboxapi.services.OrderService;
import com.foodboxapi.services.UserService;


@RestController
@RequestMapping("api/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getorders")
	public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,@RequestParam(required = false) String username){
		try {
			List<Order> list = orderService.getUserOrders(pageNo, pageSize, sortBy,username);
			return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK); 
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to fetch orders", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
	
	@GetMapping("/getorder/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable("orderId") int id) {
		try {
			Optional<Order> order = this.orderService.getOrderById(id);
			if(!(order.isPresent())) {
				return new ResponseEntity<>("Orders does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else {
				return new ResponseEntity<>(order,new HttpHeaders(), HttpStatus.OK);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>("Unable to fetch orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/addorder/{uid}")
	public ResponseEntity<?> addOrder (@PathVariable int uid, @RequestParam List<Integer> pid , @RequestParam String address,@RequestParam String phoneNo){
			
		if(pid == null || address == null || phoneNo == null ) {
			return new ResponseEntity<>("Insuffiecient data received", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			Order od = this.orderService.addOrder(uid, pid, address, phoneNo);
			return new ResponseEntity<>(od,new HttpHeaders(), HttpStatus.CREATED);
		} 
		catch (Exception e) {
			return new ResponseEntity<>("Unable to add orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateorder/{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable("orderId") int id, @RequestBody(required = true) Order updateOrder,@RequestParam(required = false) String userName) {
		if(updateOrder == null) {
			return new ResponseEntity<>("Update Order request body cannot be empty",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(id != updateOrder.getId()) {
			return new ResponseEntity<>("Id in request path and request body do not match",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			boolean check = userService.checkAdmin(userName);
			if(check) {
				Optional<Order> getOrder = this.orderService.getOrderById(id);
				if(!(getOrder.isPresent())) {
					return new ResponseEntity<>("Order does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
				}
				this.orderService.updateOrder(updateOrder);
				return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<>("Unauthorized Request",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to update orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteorder/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable("orderId") int id,@RequestParam(required = true) String userName){
		try {
			boolean check = userService.checkAdmin(userName);
			if(check) {
				Optional<Order> getOrder = this.orderService.getOrderById(id);
				if(!(getOrder.isPresent())) {
					return new ResponseEntity<>("Order does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
				}
				else {
					this.orderService.deleteOrderById(id);
					return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
				}
			}
			else {
				return new ResponseEntity<>("Unauthorized Request",new HttpHeaders(), HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to delete orders",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
