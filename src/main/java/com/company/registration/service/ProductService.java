package com.company.registration.service;

import com.company.registration.dao.ProductDAO;
import com.company.registration.domain.Product;

import java.math.BigDecimal;
import java.util.Scanner;

public class ProductService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void buildMenu(int op) {

        switch (op) {
            case 1 -> saveProduct();
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
}
