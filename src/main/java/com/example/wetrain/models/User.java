package com.example.wetrain.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity // JPA entity = mapped to a table in the db
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String role = ""; //one user can have a single role
    private String description;
    private String phone;
    @Column(nullable = false)
    private String email;
    //    private int active;
    @ManyToMany
    @JoinTable(name = "users_teams", // this is the owner side bc here we configure the relationship
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") })
    private List<Echipa> echipe = new ArrayList();//list is ordered vs Set is unordered and distinct elements
    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //    private String permissions = "";


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String username, String password, String role, String description, String phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.description = description;
        this.phone = phone;
//        this.permissions = permissions;
//        this.active = 1;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

//    public int getActive() {
//        return active;
//    }
public String getEmail() {
    return email;
}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setActive(int active) {
//        this.active = active;
//    }

    public List<Echipa> getEchipe() {
        return echipe;
    }

    public void setEchipe(List<Echipa> echipe) {
        this.echipe = echipe;
    }

    public void setRole(String role) {
        this.role = role;
    }
//    public String getPermissions() {
//        return permissions;
//    }

//    public List<String> getRoleList() {
//        if (this.roles.length() > 0) {
//            return Arrays.asList(this.roles.split(","));
//        }
//        return new ArrayList<>();
//    }

//    public List<String> getPermissionList() {
//        if (this.permissions.length() > 0) {
//            return Arrays.asList(this.permissions.split(","));
//        }
//        return new ArrayList<>();
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}