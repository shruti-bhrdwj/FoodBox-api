package com.foodboxapi.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int id;
	
	@Column(name = "amount")
	private double totalPrice;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phoneNo")
	private String phoneNo;
	
	@Column(name="DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private Date date;
	
	@ManyToMany
	@JoinTable(
	name="order_product",
	joinColumns = @JoinColumn( name="orderId"),
	inverseJoinColumns = @JoinColumn( name="productId")
	)
	private List<Product> products;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private User user;

	public Order() {
	}
	
	public Order(double totalPrice, String email, String address, String phoneNo, Date date,
			List<Product> products, User user) {
		this.totalPrice = totalPrice;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
		this.date = date;
		this.products = products;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", MSRP=" + totalPrice + ", email=" + email + ", address=" + address
				+ ", Contact=" + phoneNo + ", Date=" + date + ", products=" + products + ", user=" + user
				+ "]";
	}

}

