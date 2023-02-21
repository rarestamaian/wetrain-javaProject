package com.example.wetrain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Antrernament {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;

    private String description;

    @JsonIgnore// this annotation stops the circular reference problem
    @ManyToMany(mappedBy = "antrenamente") //this is the raget side
    private List<Echipa> echipe = new ArrayList();

    @ManyToMany
    @JoinTable(name = "antrernament_exercitiu", // this is the owner side bc here we configure the ralationship
            joinColumns = { @JoinColumn(name = "antrenament_id") },
            inverseJoinColumns = { @JoinColumn(name = "exercitiu_id") })
    private List<Exercitiu> exercitii = new ArrayList();
    public Antrernament() {
    }

    public Antrernament(long id, String name, String description, List<Echipa> echipe, List<Exercitiu> exercitii) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.echipe = echipe;
        this.exercitii = exercitii;
    }

    public List<Echipa> getEchipe() {
        return echipe;
    }

    public void setEchipe(List<Echipa> echipe) {
        this.echipe = echipe;
    }

    public List<Exercitiu> getExercitii() {
        return exercitii;
    }

    public void setExercitii(List<Exercitiu> exercitii) {
        this.exercitii = exercitii;
    }

    // variables are private so they can be accessed outside the class using public getters and setters. for example when creating an antrenament object or selecting one from the db like in AntrenamentController, if you don t have getters and setters, you cannot access their fields and thymeleaf shows error when trying to write ${antre.id} for eg
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

    @Override
    public String toString() {
        return "Antrernament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
//                ", echipe=" + echipe +
                '}';
    }
}
