package com.pb.fashion.product_category;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product-category")

public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getProductCategory() {
        return productCategoryService.getProductCategoryList();
    }

    @PostMapping
    public ResponseEntity<?> saveProductCategory(@Valid @RequestBody ProductCategory productCategory, BindingResult result) {

        //Field Validation
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        try {
            // Try to create
            productCategory.setId(null); //ignore id if provided
            ProductCategory newProductCategory = productCategoryService.saveProductCategory(productCategory);
            return new ResponseEntity<ProductCategory>(newProductCategory, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            // Handle unique constraint violation
            return new ResponseEntity<String>("Name already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle other exceptions
            return new ResponseEntity<String>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?>  editProductCategory(@Valid @RequestBody ProductCategory productCategory, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
       try{
           ProductCategory editedProductCategory = productCategoryService.editProductCategory(productCategory);
           return new ResponseEntity<ProductCategory>(editedProductCategory, HttpStatus.OK);
       }catch (DataIntegrityViolationException e){
           return new ResponseEntity<String>("Name already exist", HttpStatus.CONFLICT);

       }catch (NoSuchElementException e){
           return new ResponseEntity<String>("Invalid Id",HttpStatus.NOT_FOUND);
       }
       catch (Exception e){
           return new ResponseEntity<String>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteProductCategory(@RequestBody ProductCategory productCategory ){

        try{
           ProductCategory deletedProductCategory= productCategoryService.deleteProductCategory(productCategory.getId());
            return new ResponseEntity<ProductCategory>(deletedProductCategory,HttpStatus.GONE);
        }catch (NoSuchElementException e){
            return new ResponseEntity<String>("Invalid Id",HttpStatus.NOT_FOUND);
        }
    }
}

