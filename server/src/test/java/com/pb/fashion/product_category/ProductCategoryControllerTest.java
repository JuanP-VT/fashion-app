package com.pb.fashion.product_category;

import com.pb.fashion.product_category.ProductCategory;
import com.pb.fashion.product_category.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductCategoryController.class)
class ProductCategoryControllerTest {

    @MockBean
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryController productCategoryController;

    @Test
    void getProductCategory() {
        //Given
        ProductCategory productCategory1 = new ProductCategory("woman clothing", "https://www.icon.com");
        ProductCategory productCategory2 = new ProductCategory("man clothing", "https://www.icon.com");
        List<ProductCategory> productCategoryList = List.of(productCategory1,productCategory2);
        when(productCategoryService.getProductCategoryList()).thenReturn(productCategoryList);
        //When
        List<ProductCategory> result =  productCategoryController.getProductCategory();
        //Then
        assertEquals(productCategoryList,result);
    }

    @Test
    public void testSaveProductCategory_ValidationError() {
        // Given
        ProductCategory productCategory = new ProductCategory();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError fieldError = new FieldError("productCategory", "field", "error message");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

        // When
        ResponseEntity<?> response = productCategoryController.saveProductCategory(productCategory, bindingResult);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // More assertions to ensure the error messages are returned as expected
    }

    @Test
    public void testSaveProductCategory_DataIntegrityViolation() {
        // Given
        ProductCategory productCategory = new ProductCategory("SampleName","https://www.google.com");

        BindingResult bindingResult = mock(BindingResult.class);
        // No validation errors
        when(bindingResult.hasErrors()).thenReturn(false);

        // Simulate DataIntegrityViolationException on save due to a unique constraint violation
        when(productCategoryService.saveProductCategory(any())).thenThrow(DataIntegrityViolationException.class);

        // When
        ResponseEntity<?> response = productCategoryController.saveProductCategory(productCategory, bindingResult);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
    @Test
    public void testSaveProductCategory_InvalidRequest_NullName() {
        // Given
        ProductCategory productCategory = new ProductCategory(null,"https://www.google.com");

        BindingResult bindingResult = mock(BindingResult.class);
        // Indicate validation errors
        when(bindingResult.hasErrors()).thenReturn(true);

        // Simulate field error for name attribute
        FieldError fieldError = new FieldError("productCategory", "name", "Name cannot be null");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

        // When
        ResponseEntity<?> response = productCategoryController.saveProductCategory(productCategory, bindingResult);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testEditProductCategory_ValidId(){}

    @Test
    public void testDeleteProductCategory_ValidId(){
        //Given
        ProductCategory productCategory1 = new ProductCategory("woman clothing","https://www.icon.com");
        productCategory1.setId(1L);
        when(productCategoryService.deleteProductCategory(productCategory1.getId())).thenReturn(productCategory1);
        //When
       ResponseEntity<?> response = productCategoryController.deleteProductCategory(productCategory1);
        //Then
        assertEquals(response.getBody(),productCategory1);
    }
    @Test

    public void testDeleteProductCategory_InvalidId(){
        //Given
        ProductCategory productCategory1 = new ProductCategory("woman clothing","https://www.icon.com");
        productCategory1.setId(1L);
        when(productCategoryService.deleteProductCategory(productCategory1.getId())).thenThrow( new NoSuchElementException());
        //When
        ResponseEntity<?> response = productCategoryController.deleteProductCategory(productCategory1);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void shouldEditProductCategoryWhenValidDataIsProvided() {
        // Given
        BindingResult bindingResult = mock(BindingResult.class);
        ProductCategory validProductCategory = new ProductCategory(); // Setup data

        given(bindingResult.hasErrors()).willReturn(false);
        given(productCategoryService.editProductCategory(any(ProductCategory.class))).willReturn(validProductCategory);

        // When
        ResponseEntity<?> responseEntity = productCategoryController.editProductCategory(validProductCategory, bindingResult);

        // Then
        then(productCategoryService).should().editProductCategory(any(ProductCategory.class));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(validProductCategory);
    }

    @Test
    public void shouldReturnBadRequestWhenValidationFails() {
        // Given
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.hasErrors()).willReturn(true);
        // You can add specific errors to the bindingResult mock if needed for assertion

        // When
        ResponseEntity<?> responseEntity = productCategoryController.editProductCategory(new ProductCategory(), bindingResult);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
     }
}