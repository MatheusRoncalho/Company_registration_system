package com.company.registration.domain;

import java.util.Objects;

public class Client {
    private final Integer id;
    private final String firstName;
    private final String email;

    public Client(Integer id, String firstName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(firstName, client.firstName) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, email);
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }


    public static final class ClientBuilder {
        private Integer id;
        private String firstName;
        private String email;

        public ClientBuilder() {
        }

        public ClientBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public ClientBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Client build() {
            return new Client(id, firstName, email);
        }
    }

    @Override
    public String  toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
