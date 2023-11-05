package com.pb.fashion.product_category;

import com.pb.fashion.product_category.ProductCategory;
import com.pb.fashion.product_category.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    //Request category list
    public List<ProductCategory> getProductCategoryList() {
        return productCategoryRepository.findAll();
    }

    //Save new category
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    //Delete category
    public ProductCategory deleteProductCategory(Long id){
            //check if category product with id exist
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(id);
        if(optionalProductCategory.isPresent()){
            productCategoryRepository.deleteById(id);

            return  optionalProductCategory.get();
        }else{
            throw  new NoSuchElementException("Id not found");
        }
    }
    //Receive an existing id ProductCategoryClass to be updated
    public ProductCategory editProductCategory(ProductCategory productCategory){

        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(productCategory.getId());
        //if category exist
        if (optionalProductCategory.isPresent()){
            ProductCategory existingProductCategory = optionalProductCategory.get();
            existingProductCategory.setName(productCategory.getName());
            existingProductCategory.setImageUrl(productCategory.getImageUrl());
            return productCategoryRepository.save(existingProductCategory);
        }else {
            throw new NoSuchElementException("Id not found");
        }

    }
}
