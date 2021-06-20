package com.example.busbooking.Userhelper;

public class UsersData {
    String name;
    String mobile;
    String cnic;
    String email;
    String password;
    String dateofbirth;
    String gender;


    public UsersData() {

    }

    public UsersData(String name, String mobile, String cnic, String email, String password, String dateofbirth, String gender) {
        this.name = name;
        this.mobile = mobile;
        this.cnic = cnic;
        this.email = email;
        this.password = password;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}