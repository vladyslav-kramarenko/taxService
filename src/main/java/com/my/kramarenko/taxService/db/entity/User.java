package com.my.kramarenko.taxService.db.entity;

/**
 * User entity.
 *
 * @author Vlad Kramarenko
 */
public class User extends Entity {

    private static final long serialVersionUID = -6889036256149495388L;

    private String login;

    private String password;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private int roleId;

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

    /**
     * @return the city
     */
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        return "User [login=" + login + ", firstName=" + firstName
                + ", lastName=" + lastName + ", roleId=" + roleId + ", e-mail="
                + email + ", phone=" + phone + "]";
    }

}
