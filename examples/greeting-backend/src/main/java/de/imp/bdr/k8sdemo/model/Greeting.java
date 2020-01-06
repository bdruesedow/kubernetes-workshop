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

    @Column(name="content")
    @JsonProperty("content")
    private String content;


    public Greeting() {
    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
