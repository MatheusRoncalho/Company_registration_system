package com.company.registration.service;

import com.company.registration.dao.ProductDAO;
import com.company.registration.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void buildMenu(int op) {

        switch (op) {
            case 1 -> saveProduct();
            case 2 -> findAllProducts();
            case 3 -> findProductById();
            case 4 -> updateProduct();
            case 5 -> deleteProduct();
        }
    }

    private static void saveProduct() {
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

    public static void findProductById() {
        System.out.println("Type the ID of the product: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Product> productById = ProductDAO.findProductById(id);
        if (productById.isEmpty()) {
            System.out.printf("Error, Product with id %d not exist\n", id);
            return;
        }
        productById.ifPresent(p ->
                System.out.printf("ID: %d, Name: %s, Price: %.2f%n", p.getId(), p.getName(), p.getPrice()));
    }

    private static void updateProduct() {
        findAllProducts();
        System.out.println("Type the ID of the product: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Product> productOptional = ProductDAO.findProductById(id);
        if (productOptional.isEmpty()) {
            System.out.printf("Error, Product with id %d not exist\n", id);
            return;
        }
        Product productFromDb = productOptional.get();
        System.out.println("Type the name ( ENTER to keep the same): ");
        String name = SCANNER.nextLine();
        name = name.isBlank() ? productFromDb.getName() : name;
        System.out.println("Type the price ( ENTER to keep the same): ");
        String price = SCANNER.nextLine().replace(",", ".");
        price = price.isBlank() ? productFromDb.getPrice().toString() : price;
        BigDecimal newPrice = new BigDecimal(price);
        ProductDAO.updateProduct(new Product.ProductBuilder()
                .id(id)
                .name(name)
                .price(newPrice)
                .build());
    }

    private static void deleteProduct() {
        findAllProducts();
        System.out.println("Type the ID of the product you want to delete: ");
        int id = Integer.parseInt(SCANNER.nextLine());
        ProductDAO.deleteProductById(id);
    }
}
