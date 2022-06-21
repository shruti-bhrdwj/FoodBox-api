package com.foodboxapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodboxapi.models.Product;
import com.foodboxapi.repos.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
    private ProductRepo productRepository;
	

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> findProductByCategory(String category) {
		List<Product> list = productRepository.findAll();
		List<Product> listByCategory = new ArrayList<>();
		for (Product p : list) {
			if(p.getCategory().toLowerCase().contains(category.toLowerCase())) {
				listByCategory.add(p);
			}
		}
		return listByCategory;
	}
	
	public List<Product> findProductByVendor(String sellerName) {
		List<Product> list = productRepository.findAll();
		List<Product> listByVendor = new ArrayList<>();
		for (Product p : list) {
			if(p.getSeller().toLowerCase().contains(sellerName.toLowerCase())) {
				listByVendor.add(p);
			}
		}
		return listByVendor;
	}
	
	public List<Product> findProductByName(String productName) {
		List<Product> list = productRepository.findAll();
		List<Product> listByName = new ArrayList<>();
		for (Product p : list) {
			if(p.getName().toLowerCase().contains(productName.toLowerCase())) {
				listByName.add(p);
			}
		}
		return listByName;
	}
	
	public Product findProductById(int productId) {
		return productRepository.findById(productId).get();
	}

	public void deleteProduct(int pId) {
		productRepository.deleteById(pId);
	}

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public String updateProduct(int productId,Product product ) {
		String str = "";
		if (productRepository.existsById(productId)) {
			Product pr = productRepository.findById(productId).get();
			pr = product;
			productRepository.save(pr);
			str.concat("Product Details Updated!");
		}
		return str;	
		
	}

}
