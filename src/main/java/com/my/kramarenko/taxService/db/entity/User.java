package com.my.kramarenko.taxService.db.entity;

import com.my.kramarenko.taxService.db.enums.Role;

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
    private String codePassport;
    private int roleId;

    public User() {
    }

    public User(String email, String password, String name, String surname, String patronymic, String codePassport, String phone, int roleId) {
        this.firstName = name;
        this.lastName = surname;
        this.patronymic = patronymic;
        this.codePassport = codePassport;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
    }


    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCodePassport() {
        return codePassport;
    }

    public void setCodePassport(String codePassport) {
        this.codePassport = codePassport;
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
//                "login=" + login + ", " +
                "firstName=" + firstName
                + ", lastName=" + lastName + ", patronymic=" + patronymic + ", codePassport=" + codePassport + ", roleId=" + roleId + ", e-mail="
                + email + ", phone=" + phone + "]";
    }
}
