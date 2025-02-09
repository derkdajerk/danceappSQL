package com.example;

public class danceClass implements Measurable{
    private int id;
    private String className;
    private String instructor;
    private String price;
    private String time;
    private String length;
    private String date;

    public danceClass(){} // default constructor

    public danceClass(int id, String className, String instructor, String price, String time, String length, String date) {
        this.id = id;
        this.className = className;
        this.instructor = instructor;
        this.price = price;
        this.time = time;
        this.length = length;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "danceClass [className=" + className + ", instructor=" + instructor + ", price=" + price + ", time="
                + time + ", length=" + length + ", date=" + date + "]";
    }

    @Override
    public int getMeasure() {
        return id;
    }

    
}
