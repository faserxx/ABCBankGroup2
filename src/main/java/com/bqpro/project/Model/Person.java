package com.bqpro.project.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;

    @OneToMany(mappedBy = "person")
    private List<Address> address= new ArrayList<>();

    private Date dateOfBirth;
    private String phoneNumbers;
    private String personalPhoto;

    // Constructor, getters y setters


    public Person(){}

    public Person(String firstName, String secondName, Date dateOfBirth, String phoneNumbers, String personalPhoto) {
        this.firstName = firstName;
        this.secondName = secondName;

        this.dateOfBirth = dateOfBirth;
        this.phoneNumbers = phoneNumbers;
        this.personalPhoto = personalPhoto;
    }
    public Person(String firstName, String secondName, Date dateOfBirth, String phoneNumbers) {
        this.firstName = firstName;
        this.secondName = secondName;

        this.dateOfBirth = dateOfBirth;
        this.phoneNumbers = phoneNumbers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Address> getAddresses() {
        return this.address;
    }

    public void setAddresses(List<Address> addresses) {
        this.address = addresses;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getPersonalPhoto() {
        return this.personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }
}

