package com.glady.challenge.model.entities;

import jakarta.persistence.*;

import java.util.Set;

@Table(name = "user")
@Entity
public class User {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Deposit> deposits;

    @Id
    private String userName;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public User(String userName, Company company) {
        this.userName = userName;
        this.company = company;
    }

    public User() {
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
