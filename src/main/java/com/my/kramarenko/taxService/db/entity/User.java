package com.my.kramarenko.taxService.db.entity;

import java.io.Serial;

/**
 * User entity.
 *
 * @author Vlad Kramarenko
 */
public class User extends Entity {

    @Serial
    private static final long serialVersionUID = -6889036256149495388L;

    //    private String login;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String code;
    private int roleId;

    private String companyName;
    private boolean isBanned = false;
    private boolean isIndividual = true;


    public User() {
    }

    public boolean isIndividual() {
        return isIndividual;
    }

    public void setIndividual(boolean individual) {
        isIndividual = individual;
    }

    public void setIndividual(int individual) {
        isIndividual = individual == 1;
    }

    public void setIndividual(String individual) {
        isIndividual = Boolean.parseBoolean(individual);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        this.isBanned = banned;
    }

    public void setBanned(int banned) {
        isBanned = banned == 1;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User [" +
                "companyName=" + companyName +
                ", code=" + code + ", " +
                ", is Individual=" + isIndividual +
                ", roleId=" + roleId +
                ", e-mail=" + email +
                ", phone=" + phone + "]";
    }
}
