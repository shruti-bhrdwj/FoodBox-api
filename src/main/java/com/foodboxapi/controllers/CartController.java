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

import com.foodboxapi.models.Cart;
import com.foodboxapi.services.CartService;
import com.foodboxapi.services.UserService;

@RestController
@RequestMapping("api/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getcarts")
	public ResponseEntity<?> getCarts(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,@RequestParam(required = false) String username){
		try {
			List<Cart> list = cartService.getEntityCarts(pageNo, pageSize, sortBy,username);
			return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK); 
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to fetch carts", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/getcart/{cartId}")
	public ResponseEntity<?> getCart(@PathVariable("cartId") int id) {
		try {
			Optional<Cart> cart = this.cartService.getEntityCart(id);
			if(!(cart.isPresent())) {
				return new ResponseEntity<>("Cart does not exist with id " + id, new HttpHeaders(), HttpStatus.NOT_FOUND); 
			}
			else {
				return new ResponseEntity<>(cart,new HttpHeaders(), HttpStatus.OK);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>("Unable to fetch carts",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/addcart")
	public ResponseEntity<?> addCart(@RequestBody(required = true) Cart addCart){
		if(addCart == null) {
			return new ResponseEntity<>("Add Cart request body cannot be empty", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			this.cartService.addEntityCart(addCart);
			return new ResponseEntity<>(addCart, new HttpHeaders(), HttpStatus.CREATED);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Unable to add cart",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updatecart/{cartId}")
	public ResponseEntity<?> updateCart(@PathVariable("cartId") int id, @RequestBody(required = true) Cart updateCart) {
		if(updateCart == null) {
			return new ResponseEntity<>("Update Cart request body cannot be empty",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		if(id != updateCart.getId()) {
			return new ResponseEntity<>("Id in request path and request body do not match",new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		try {
			Optional<Cart> getCart= this.cartService.getEntityCart(id);
			if(!(getCart.isPresent())) {
				return new ResponseEntity<>("Cart does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			this.cartService.updateEntityCart(updateCart);
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to update cart",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deletecart/{cartId}")
	public ResponseEntity<?> deleteCart(@PathVariable("cartId") int id){
		try {
			Optional<Cart> getCart = this.cartService.getEntityCart(id);
			if(!(getCart.isPresent())) {
				return new ResponseEntity<>("Cart does not exist with id " + id,new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			else {
				this.cartService.deleteEntityCart(id);
				return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception ex) {
			return new ResponseEntity<>("Unable to delete cart",new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
