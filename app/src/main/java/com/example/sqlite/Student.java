package com.example.sqlite;

public class Student {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String avatar;

    public Student(String id, String name, String address, String phone, String avatar) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public Student() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
