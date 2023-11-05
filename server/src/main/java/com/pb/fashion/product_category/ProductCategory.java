package com.pb.fashion.product_category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class ProductCategory {
    private @Id
    @GeneratedValue Long id;

    @Column(unique = true)
    @NotNull
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotNull
    private String imageUrl;

    public ProductCategory() {
    }

    public ProductCategory(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    //Getters
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "id="+id+" name="+name+" imageUrl="+imageUrl;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ProductCategory productCategory)) return false;
        return Objects.equals(this.id, productCategory.id) && Objects.equals(this.name, productCategory.name) && Objects.equals(this.imageUrl, productCategory.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.imageUrl);
    }
}

