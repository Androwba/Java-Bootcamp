package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductsRepositoryJdbcImplTest {

    private ProductsRepository productsRepository;

    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "Product1", 10.00),
            new Product(2L, "Product2", 20.00),
            new Product(3L, "Product3", 30.00),
            new Product(4L, "Product4", 40.00),
            new Product(5L, "Product5", 50.00)
    );
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Product1", 10.00);
    private static final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "UpdatedProduct", 100.00);
    private static final Product EXPECTED_SAVED_PRODUCT = new Product(null, "NewProduct", 60.00);

    @BeforeEach
    void setUp() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(db);
    }

    @Test
    void testFindAll() {
        List<Product> products = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products, "The list of products should match the expected list");
    }

    @Test
    void testFindById() {
        Optional<Product> product = productsRepository.findById(1L);
        assertTrue(product.isPresent(), "Product with ID 1 should be present");
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get(), "The product should match the expected product");
    }

    @Test
    void testUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> updatedProduct = productsRepository.findById(1L);
        assertTrue(updatedProduct.isPresent(), "Updated product should be present");
        assertEquals(EXPECTED_UPDATED_PRODUCT, updatedProduct.get(), "The updated product should match the expected product");
    }

    @Test
    void testSave() {
        productsRepository.save(EXPECTED_SAVED_PRODUCT);
        List<Product> products = productsRepository.findAll();
        assertEquals(6, products.size(), "There should be 6 products in the database");

        boolean newProductFound = false;
        for (Product product : products) {
            if ("NewProduct".equals(product.getName()) && product.getPrice() == 60.00) {
                newProductFound = true;
                break;
            }
        }
        assertTrue(newProductFound, "The new product should be present in the database");
    }

    @Test
    void testDelete() {
        productsRepository.delete(1L);
        Optional<Product> product = productsRepository.findById(1L);
        assertFalse(product.isPresent(), "Product with ID 1 should be deleted");
        List<Product> products = productsRepository.findAll();
        assertEquals(4, products.size(), "There should be 4 products in the database");
    }
}
