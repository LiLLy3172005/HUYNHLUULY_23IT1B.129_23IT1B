package com.example.qlnn1;

public class IdUsers {
    private int idUser;
    private String userName;
    private String passWords;
    private String email;
    private String phoneNumber;

    // Constructor
    public IdUsers(int idUser, String userName, String passWords, String email, String phoneNumber) {
        this.idUser = idUser;
        this.userName = userName;
        this.passWords = passWords;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for idUser
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // Getter and Setter for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter and Setter for passWords
    public String getPassWords() {
        return passWords;
    }

    public void setPassWords(String passWords) {
        this.passWords = passWords;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Signup{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", passWords='" + passWords + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}
