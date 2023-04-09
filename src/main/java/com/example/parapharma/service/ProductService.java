package com.example.parapharma.service;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.Product;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private static String BASE_PATH = "C:/Users/JONN OPPONG/IdeaProjects/parapharma/src/main/resources/assets";



    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAll(){
        return this.productRepository.findAll();
    }

    public Product saveProduct(Product product){

        return this.productRepository.save(product);
    }

    public Product fetchProduct(Long id){
        return this.productRepository.getReferenceById(id);
    }

    public void deleteProduct(Product product){
        this.productRepository.delete(product);
    }

    void updateQuantityInStock(List<OrderDetail> details){
        List<Product> products = new ArrayList<>();

        for(OrderDetail detail: details){
            Product product = detail.getProduct();
            product.setQtyInStock(
                    product.getQtyInStock() - detail.getQuantity()
            );
            products.add(product);
        }
        this.productRepository.saveAll(products);
    }

}
