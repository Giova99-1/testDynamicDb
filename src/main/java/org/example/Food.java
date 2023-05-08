package org.example;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class Food implements Serializable {
    @Column(name = "food")
    private String name;
    @Column(name = "food_type")
    private String type;

    @Override
    public String toString() {
        return type + "\t|\t" + name;
    }
}