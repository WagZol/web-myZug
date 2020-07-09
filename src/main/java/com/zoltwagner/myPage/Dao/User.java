package com.zoltwagner.myPage.Dao;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String email;
    private String password;
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String userName, String email, String password, String avatar) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String userName, String email, String password, String avatar, Collection<Role> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User userToCompare = (User) o;

        return this.id == userToCompare.getId();
    }
}
