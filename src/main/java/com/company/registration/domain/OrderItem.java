package com.company.registration.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final Integer id;
    private final Orders order;
    private final Product product;
    private final int quantity;
    private final BigDecimal price;

    public OrderItem(Integer id, Orders order, Product product, int quantity, BigDecimal price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OrderItem orderItem)) return false;
        return quantity == orderItem.quantity && Objects.equals(id, orderItem.id) && Objects.equals(order, orderItem.order) && Objects.equals(product, orderItem.product) && Objects.equals(price, orderItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, quantity, price);
    }

    public Integer getId() {
        return id;
    }

    public Orders getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static final class OrderItemBuilder {
        private Integer id;
        private Orders order;
        private Product product;
        private int quantity;
        private BigDecimal price;

        public OrderItemBuilder() {
        }

        public OrderItemBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder order(Orders order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public OrderItemBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(id, order, product, quantity, price);
        }
    }
}
