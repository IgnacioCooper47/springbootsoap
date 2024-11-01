package com.springbootsoap.soap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "vehicleDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vehicle {

    @Id
    @XmlElement(name = "brand")
    private String brand;

    @XmlElement(name = "model")
    private String model;

    @XmlElement(name = "color")
    private String color;

    // Constructor, Getters y Setters
    public Vehicle() {}

    public Vehicle(String brand, String model, String color) {
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}