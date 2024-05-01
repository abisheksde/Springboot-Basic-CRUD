package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Repository.ProductRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.Model.ProductModel;

@Controller
public class ProductController {

	@GetMapping("/product")
	public String Product(Model model) {
		model.addAttribute("message", "Enter Your product Details");
		return "product";
	}

	@Autowired
	private ProductRepository productRepository;

	@PostMapping("/save-product")
	public String Product(ProductModel productData, Model model) {

		ProductModel n = new ProductModel();
		n.setName(productData.getName());
		n.setDescription(productData.getDescription());
		n.setPrice(productData.getPrice());
		productRepository.save(n);

		model.addAttribute("message", "The product " + productData.getName() + " is saved successfully");
		return "product";
	}

	@GetMapping("/products")
	public String showProducts(Model model) {
		Iterable<ProductModel> productList = productRepository.findAll();
		model.addAttribute("products", productList);
		return "products";
	}

	@GetMapping("/update/{id}")
	public String updateProduct(@PathVariable Integer id, Model model) {
		Optional<ProductModel> optionalProductDetails = productRepository.findById(id);
		ProductModel productDetails = optionalProductDetails.get();

		model.addAttribute("id", id);
		model.addAttribute("productDetails", productDetails);
		return "update";
	}

	@PostMapping("/update/{id}")
	public String updateProduct(@PathVariable Integer id, String name, String description, float price, Model model) {
		Optional<ProductModel> optionalProductDetails = productRepository.findById(id);
		ProductModel productDetails = optionalProductDetails.get();
		productDetails.setName(name);
		productDetails.setDescription(description);
		productDetails.setPrice(price);
		productRepository.save(productDetails);
		return "redirect:/products";
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Integer id, Model model) {
		Optional<ProductModel> optionalProductDetails = productRepository.findById(id);
		ProductModel productDetails = optionalProductDetails.get();
		model.addAttribute("id", id);
		model.addAttribute("productDetails", productDetails);
		return "delete";
	}

	@PostMapping("/delete/{id}")
	public String deleteProduct(String name, Model model) {
	
		if (name != null && !name.isEmpty()) { 
			ProductModel productDetails = productRepository.findByname(name); 
			productRepository.delete(productDetails);
			return "redirect:/products"; 
		} 
		
		return "delete";
		 
	}

}