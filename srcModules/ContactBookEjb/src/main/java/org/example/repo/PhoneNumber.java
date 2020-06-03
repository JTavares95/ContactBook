package org.example.repo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="phone_number", schema = "public")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NUMBER")
    private String number;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CONTACT_ID")
    private Contact contact;


    public PhoneNumber() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @JsonIgnore
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(description, that.description) &&
               Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, number);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", number='" + number + '\'' +
               '}';
    }
}

