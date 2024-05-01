package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.Model.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel,Integer> {

	ProductModel findByname(String name);

}