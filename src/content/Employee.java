/**
 * Ayah Mahmoud & Arushi Sood
 * 991647463 & 991644338
 * Final Project
 * April 17, 2022
 */
package content;

import java.util.ArrayList;

public class Employee {
    private int idNumber;
    private String name;
    private String city;
    private String position;
    
    // Employee constructor takes an integer parameter for Employee ID
    public Employee(int idNumber) {
        this.idNumber = idNumber;
    }
    
    // Setters for Employee data fields
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    // Getters for Employee data fields
    public int getIdNumber() {
        return idNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }

    public String getPosition() {
        return position;
    }
}