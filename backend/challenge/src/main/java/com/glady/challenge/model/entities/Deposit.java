package com.glady.challenge.model.entities;

import com.glady.challenge.model.dto.EnumDepositType;
import com.glady.challenge.model.dto.DistributeDepositDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "deposits")
@Entity
public class Deposit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    private EnumDepositType type;
    private LocalDate receivedDate;
    private int price;

    public Deposit() {
    }

    public Deposit(DistributeDepositDTO distributeDepositDTO, User user) {
        this.type = distributeDepositDTO.getDepositType();
        this.price = distributeDepositDTO.getPrice();
        this.receivedDate = LocalDate.now();
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnumDepositType getType() {
        return type;
    }

    public void setType(EnumDepositType type) {
        this.type = type;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
