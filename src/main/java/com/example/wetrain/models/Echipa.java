package com.example.wetrain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="teams")
public class Echipa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)// the column cannot be null
    private String name;

    private String description;
    @ManyToMany
    @JoinTable(name = "teams_antrenament", // this is the owner side bc here we configure the ralationship
            joinColumns = { @JoinColumn(name = "team_id") },
            inverseJoinColumns = { @JoinColumn(name = "antrenament_id") })
    private List<Antrernament> antrenamente = new ArrayList();//list is ordered vs Set is unordered and distinct elements

    @JsonIgnore// this annotation stops the circular reference problem
    @ManyToMany(mappedBy = "echipe") //this is the raget side
    private List<User> utilizatori = new ArrayList();
    public List<Antrernament> getAntrenamente() {
        return antrenamente;
    }

    public Echipa(String name, String description, long id) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Echipa() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAntrenamente(List<Antrernament> antrenamente) {
        this.antrenamente = antrenamente;
    }


    @Override
    public String toString() {
        return "Echipa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
//                ", antrenamente=" + antrenamente +
                '}';
    }
}
