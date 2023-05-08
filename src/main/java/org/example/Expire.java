package org.example;

import javax.persistence.*;
@Entity
public class Expire {
    @Column(name = "food_type")
    private String type;
    @Id
    @Column(name = "food")
    private String food;
    @Column(name = "storage_type")
    private String storage;
    @Column(name = "days")
    private int days;

    public Expire(String type, String food,  String storage, int days) {
        this.food = food;
        this.storage = storage;
        this.days = days;
    }

    public Expire() {}

    @Override
    public String toString() {
        return String.format("%s\t|\t%s\t|\t%s\t|\t%d", type, food, storage, days);
    }
}
