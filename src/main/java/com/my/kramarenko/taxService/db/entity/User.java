package com.my.kramarenko.taxService.db.entity;

import java.io.Serial;
import java.util.Objects;

/**
 * User entity.
 *
 * @author Vlad Kramarenko
 */
public class User extends Entity {

    @Serial
    private static final long serialVersionUID = -6889036256149495388L;

    private String password;
    private String email;
    private String code;
    private int roleId;

    private String companyName;
    private boolean isBanned = false;
    private int legalType;


    public User() {
    }

    public int getLegalType() {
        return legalType;
    }

    public void setLegalType(int legalType) {
        this.legalType = legalType;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (getId() != user.getId()) return false;
        if (!email.equals(user.email)) return false;
        return Objects.equals(code, user.code);
    }

    @Override
    public int hashCode() {
        int result = getId() + email.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User [" +
                "companyName=" + companyName +
                ", code=" + code + ", " +
                ", Legal type id=" + legalType +
                ", roleId=" + roleId +
                ", e-mail=" + email + "]";
    }
}
