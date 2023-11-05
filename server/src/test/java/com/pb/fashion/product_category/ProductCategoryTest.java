package com.pb.fashion.product_category;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductCategoryTest {

    @Autowired
    private EntityManager entityManager;

    //Should throw a ConstraintViolation exception if name is null
    @Test
    void throwsExceptionOnNullName() {
        ProductCategory productCategory = new ProductCategory(null, "https//:www.icon.com");

        //Catch exception
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(productCategory);
            entityManager.flush();
        });

        assertNotNull(exception);
    }

    //Test for no duplicates
    @Test
    void throwExceptionOnRepeatedName() {
        ProductCategory productCategory1 = new ProductCategory("clothing", "https://www.icon.com");
        ProductCategory productCategory2 = new ProductCategory("clothing", "https://www.icon.com");

        //Catch exception
        entityManager.persist(productCategory1);
        entityManager.flush();
        org.hibernate.exception.ConstraintViolationException exception = assertThrows(org.hibernate.exception.ConstraintViolationException.class, () -> {

            entityManager.persist(productCategory2);
            entityManager.flush();

        });
        assertNotNull(exception);
    }

    @Test
    void testToString() {
        ProductCategory productCategory = new ProductCategory("woman clothing", "https://www.icon.com");

        String expected = "id=null name=woman clothing imageUrl=https://www.icon.com";
        String methodTest = productCategory.toString();

        assertEquals(expected, methodTest);
    }

    @Test
    void testEquals() {
        ProductCategory productCategory1 = new ProductCategory("woman clothing", "https://www.icon.com");
        ProductCategory productCategory2 = new ProductCategory("man clothing", "https://www.icon.com");
        ProductCategory productCategory3 = new ProductCategory("woman clothing", "https://www.icon.com");

        boolean expectDifferent = productCategory1.equals(productCategory2);
        boolean expectSame = productCategory1.equals(productCategory3);
        //Products are different
        assertFalse(expectDifferent);
        //Products are the same
        assertTrue(expectSame);
    }

    @Test
    void testHashCode() {
        ProductCategory productCategory1 = new ProductCategory("woman clothing", "https://www.icon.com");
        ProductCategory productCategory2 = new ProductCategory("man clothing", "https://www.icon.com");
        ProductCategory productCategory3 = new ProductCategory("woman clothing", "https://www.icon.com");

        int hash1 = productCategory1.hashCode();
        int hash2 = productCategory2.hashCode();
        int hash3 = productCategory3.hashCode();

        assertEquals(hash1, hash3);
        assertNotEquals(hash1, hash2);
    }
}