package de.imp.bdr.k8sdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "greeting")
public class Greeting {

    @Id
    @Column(name="_id")
    @JsonProperty("id")
    private long id;

    @Column(name="name")
    @JsonProperty("name")
    private String name;


    public Greeting() {
    }

    public Greeting(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
