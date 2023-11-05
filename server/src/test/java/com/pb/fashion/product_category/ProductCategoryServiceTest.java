package com.pb.fashion.product_category;

import com.pb.fashion.product_category.ProductCategory;
import com.pb.fashion.product_category.ProductCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProductCategoryService.class)
class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryService productCategoryService;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void getProductCategoryList() {
        //Given
        ProductCategory productCategory1 = new ProductCategory("woman clothing", "https://www.icon.com");
        ProductCategory productCategory2 = new ProductCategory("man clothing", "https://www.icon.com");

        productCategory1.setId(20L);
        productCategory2.setId(21L);
        List<ProductCategory> mockList = List.of(productCategory1, productCategory2);
        when(productCategoryRepository.findAll()).thenReturn(mockList);
        //When
        List<ProductCategory> result = productCategoryService.getProductCategoryList();
        //Then
        assertEquals(result, mockList);
        verify(productCategoryRepository, times(1)).findAll();
    }

    @Test
    void saveProductCategory() {
        //Given
        ProductCategory newProductCategory = new ProductCategory("woman clothing", "https://www.icon.com");
        when(productCategoryRepository.save(newProductCategory)).thenReturn(newProductCategory);
        //When
        ProductCategory savedProductCategory = productCategoryService.saveProductCategory(newProductCategory);

        //Then
        assertEquals(newProductCategory, savedProductCategory);
        verify(productCategoryRepository).save(newProductCategory);
    }

}