package com.example.phone;

import com.example.security.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NUMBER", length = 12)
    @NotNull
    @Size(min = 7, max = 12)
    private String number;

    @Column(name = "CITYCODE", length = 1)
    @NotNull
    private String citycode;

    @Column(name = "COUNTRYCODE", length = 4)
    @NotNull
    @Size(min = 2, max = 4)
    private String countrycode;
    
    private String description;

    /*@ManyToMany(mappedBy = "phones", fetch = FetchType.LAZY)
    private List<User> users;*/

    /*@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;*/

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public Phone(){}

    public Phone(String number, String citycode, String countrycode, User user) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
