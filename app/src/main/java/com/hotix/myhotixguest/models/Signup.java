package com.hotix.myhotixguest.models;

public class Signup {

    private int hotelId;
    private int civilityId;
    private int countryId;
    private int NationalityId;

    private String userName;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String berthDate;
    private String address;

    public Signup() {
    }

    public Signup(int hotelId, int civilityId, int countryId, int nationalityId, String userName, String password, String email, String phone, String firstName, String lastName, String berthDate, String address) {
        this.hotelId = hotelId;
        this.civilityId = civilityId;
        this.countryId = countryId;
        NationalityId = nationalityId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.berthDate = berthDate;
        this.address = address;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getCivilityId() {
        return civilityId;
    }

    public void setCivilityId(int civilityId) {
        this.civilityId = civilityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getNationalityId() {
        return NationalityId;
    }

    public void setNationalityId(int nationalityId) {
        NationalityId = nationalityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBerthDate() {
        return berthDate;
    }

    public void setBerthDate(String berthDate) {
        this.berthDate = berthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Signup{" +
                "hotelId=" + hotelId +
                ", civilityId=" + civilityId +
                ", countryId=" + countryId +
                ", NationalityId=" + NationalityId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", berthDate='" + berthDate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}




