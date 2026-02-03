package com.company.registration.service;

import com.company.registration.dao.ProductDAO;
import com.company.registration.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void buildMenu(int op) {

        switch (op) {
            case 1 -> saveProduct();
            case 2 -> findAllProducts();
        }
    }

    public static void saveProduct() {
        System.out.println("Type the name of the product: ");
        String name = SCANNER.nextLine();
        System.out.println("Type the price of the product: ");
        BigDecimal price = new BigDecimal(SCANNER.nextLine().replace(",", "."));
        Product product = new Product.ProductBuilder()
                .name(name)
                .price(price)
                .build();
        ProductDAO.saveProduct(product);
    }

    public static void findAllProducts() {
        List<Product> productList = ProductDAO.findAllProducts();
        productList.forEach(p ->
                System.out.printf("ID: %d, Name: %s, Price: %.2f%n", p.getId(), p.getName(), p.getPrice()));
    }
}
