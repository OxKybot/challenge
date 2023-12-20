package com.glady.challenge.model.entities;

import com.glady.challenge.model.dto.CompanyDTO;
import jakarta.persistence.*;

import java.util.Set;

@Table(name = "company")
@Entity
public class Company {

    @Id
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<User> users;
    private int amount;

    public Company() {
    }

    public Company(CompanyDTO companyDTO) {
        this.name = companyDTO.getName();
        this.amount = companyDTO.getAmount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
