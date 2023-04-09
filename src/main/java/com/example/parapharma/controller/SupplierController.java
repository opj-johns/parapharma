package com.example.parapharma.controller;

import com.example.parapharma.domain.Supplier;
import com.example.parapharma.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/supplier")
public class SupplierController {

    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @RequestMapping("/all")
    public ResponseEntity<List<Supplier>> getSuppliers(){
        List<Supplier> suppliers = this.supplierService.getSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable("id") Long supplierId){
        Supplier supplier = this.supplierService.fetchSupplier(supplierId);
        return ResponseEntity.ok(supplier);
    }

    @RequestMapping("/save")
    public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplier){
        Supplier savedSupplier = this.supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(savedSupplier);
    }

    // delete supplier
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteSupplier(@RequestBody Supplier supplier){
        this.supplierService.deleteSupplier(supplier);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
