package com.foodboxapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodboxapi.models.Product;
import com.foodboxapi.services.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/product/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public List<Product> getProductList()
	{
		return productService.getAllProducts();
	}
	
	@GetMapping("/searchByVendor")
	public List<Product> getProductByVendor(@RequestParam String vendorName)
	{
		return productService.findProductByVendor(vendorName);
	}
	
	@GetMapping("/searchByCategory")
	public List<Product> getProductByCategory(@RequestParam String category)
	{
		return productService.findProductByCategory(category);
	}
	
	@GetMapping("/searchByName")
	public List<Product> getProductByName(@RequestParam String productName)
	{
		return productService.findProductByName(productName);
	}
	
	@GetMapping("/{productId}")
	public Product productById(@PathVariable("productId") int productId)
	{
		return productService.findProductById(productId);
	}
	
	@DeleteMapping("/{productId}/delete")
	public String deleteProduct(@PathVariable("productId") int productId)
	{
		productService.deleteProduct(productId);
		return "Product deleted successfully.";
	}
	
	@PostMapping(path="/add",
			consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody Product product)
	{
		Product pr= productService.addProduct(product);
		return new ResponseEntity<>(pr,HttpStatus.CREATED);
	}
	
	@PostMapping(path="/update/{productId}",
	  consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Product> updateProduct(@PathVariable("productId") int pId, @RequestBody Product product)
  {
   productService.updateProduct(pId,product);
   return new ResponseEntity<>(product,HttpStatus.OK);
  }	
	
}
