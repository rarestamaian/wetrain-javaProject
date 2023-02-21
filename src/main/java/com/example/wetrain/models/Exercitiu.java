package com.example.wetrain.models;

import com.example.wetrain.repositories.AntrenamentRepository;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercitiu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)// the column cannot be null
    private String name;

    private String description;

    @ManyToMany(mappedBy = "exercitii") //this is the raget side
    private List<Antrernament> antrenamente = new ArrayList();
    public Exercitiu() {
    }

    public Exercitiu(long id, String name, String description, List<Antrernament> antrenamente) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.antrenamente = antrenamente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Antrernament> getAntrenamente() {
        return antrenamente;
    }

    public void setAntrenamente(List<Antrernament> antrenamente) {
        this.antrenamente = antrenamente;
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
}
