package com.example.security.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.phone.Phone;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "FIRSTNAME", length = 50)
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name="TOKEN")
    private String token;

    @Column(name = "ISACTIVE")
    @NotNull
    private Boolean enabled;

    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @UpdateTimestamp
    @Column(name = "MODIFIED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name = "LAST_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_login;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;
    
    @JsonManagedReference
    @OneToMany(mappedBy="user", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    public User(){}

    public User(String username, String password, String email, Boolean enabled, Date created, Date modified, Date last_login, String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.created = created;
        this.modified = modified;
        this.last_login = last_login;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private List<Authority> authorities;*/

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_PHONE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PHONE_ID", referencedColumnName = "ID")})
    private List<Phone> phones;*/

    /*@OneToMany( targetEntity=Phone.class )
    private List<Phone> phones;*/


    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /*public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }*/

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}