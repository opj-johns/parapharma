package com.example.parapharma.controller;
import com.example.parapharma.domain.Product;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping("/all")
    public ResponseEntity<List<Product>> fetchAll() {
        List<Product> products = this.productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    // save product
    @RequestMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = this.productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }
    // get by id
    @RequestMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product = this.productService.fetchProduct(id);
        return ResponseEntity.ok(product);
    }
    // delete product
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product){
        this.productService.deleteProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
