package com.softuni.pcstore.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{
    
    private String city;
    private String street;
    private String number;
    private String postCode;
    private String phoneNumber;
    private User user;

    public Address() {
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "street", nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "number", nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "postcode", nullable = false)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "phoneNumber", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinTable(name = "addresses_users"
            , joinColumns = @JoinColumn(name = "address_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
