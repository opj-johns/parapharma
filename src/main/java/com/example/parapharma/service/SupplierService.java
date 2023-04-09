package com.example.parapharma.service;

import com.example.parapharma.domain.Supplier;
import com.example.parapharma.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getSuppliers(){
        return this.supplierRepository.findAll();
    }

    public Supplier fetchSupplier(Long supplierId){
        return this.supplierRepository.getReferenceById(supplierId);
    }

    public Supplier saveSupplier(Supplier supplier){
        return this.supplierRepository.save(supplier);
    }

    public void deleteSupplier(Supplier supplier){
        this.supplierRepository.delete(supplier);
    }
}
