package com.company.registration.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Orders {
    private final Integer id;
    private final Client client;
    private final LocalDateTime order_date;
    private final BigDecimal total;

    public Orders(Integer id, Client client, LocalDateTime order_date, BigDecimal total) {
        this.id = id;
        this.client = client;
        this.order_date = order_date;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", client=" + client +
                ", order_date=" + order_date +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Orders orders)) return false;
        return Objects.equals(id, orders.id) && Objects.equals(client, orders.client) && Objects.equals(order_date, orders.order_date) && Objects.equals(total, orders.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, order_date, total);
    }


    public static final class OrdersBuilder {
        private Integer id;
        private Client client;
        private LocalDateTime order_date;
        private BigDecimal total;

        public OrdersBuilder() {
        }

        public OrdersBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public OrdersBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public OrdersBuilder order_date(LocalDateTime order_date) {
            this.order_date = order_date;
            return this;
        }

        public OrdersBuilder total(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Orders build() {
            return new Orders(id, client, order_date, total);
        }
    }
}
