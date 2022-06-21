package com.foodboxapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.foodboxapi.models.Order;
import com.foodboxapi.repos.OrderRepo;
import com.foodboxapi.repos.ProductRepo;
import com.foodboxapi.repos.UserRepo;
import com.foodboxapi.models.Product;
import com.foodboxapi.models.User;

@Service
public class OrderService {
	@Autowired
	OrderRepo orderRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepo userRepository;
	
	@Autowired
	ProductRepo pRepo;
	
	public List<Order> getOrderByUsername(Integer pageNo, Integer pageSize, String sortBy, String username) throws Exception{
		Page<Order> pagedResult;
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
		User user = null;
		try {
			pagedResult =  orderRepository.findAll(paging);
			if(username != null) {
				
				Iterable<User> users = userRepository.findAll();
				for(User u : users) {
					if(u.getUsername().equals(username)) {
						user = u;
						break;
					}
				}
				pagedResult = orderRepository.findByUserContaining(user, paging);
			}
			if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<>();
	        }
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new Exception("Unable to retrieve orders "+ex.getMessage());
		}
	}
	
	public Order addOrder(int uid, List<Integer> pid, String address,String phoneNo) throws Exception {
		try {
			List<Product> prods = new ArrayList<>();
			for(Integer id : pid) {
				Product pr = pRepo.findById(id).get();
				prods.add(pr);
			}
			User us = userRepository.findById(uid).get();
			Order od = new Order();
			od.setProducts(prods);
			od.setUser(us);
			System.out.println(od.getUser().getUsername());
			double total = 0.0;
			for( Product prod : od.getProducts()) {
				System.out.print(prod.getPrice());
				total = total+ prod.getPrice();
			}
			System.out.println("total balance");
			System.out.println(total);
			od.setTotalPrice(total);
			od.setAddress(address);
			od.setEmail(us.getEmail());
			od.setPhoneNo(phoneNo);
			this.orderRepository.save(od);
			return od;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			throw new Exception("Unable to add order "+ex.getMessage());
			
		}
	}
	
	public Optional<Order> getOrderById(int orderId) throws Exception{
		try {
			return this.orderRepository.findById(orderId);
		}
		catch(Exception ex){
			throw new Exception("Unable to retrieve order with id"+orderId+" "+ex.getMessage());
		}
	}
	
	public void updateOrder(Order updateOrder) throws Exception {
		try {
			this.orderRepository.save(updateOrder);
		}
		catch(Exception ex) {
			throw new Exception("Unable to update order "+ex.getMessage());
		}
	}

	public void deleteOrderById(int orderId) throws Exception {
		try {
			this.orderRepository.deleteById(orderId);
		}
		catch(Exception ex) {
			throw new Exception("Unable to delete order "+ex.getMessage());
		}
	}
	
	
		
}

	


