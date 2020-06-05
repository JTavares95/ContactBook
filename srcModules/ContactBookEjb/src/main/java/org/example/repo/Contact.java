package org.example.repo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="contact", schema = "public")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PhoneNumber> phoneNumbers;

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        phoneNumbers.add(phoneNumber);
        phoneNumber.setContact(this);
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        phoneNumber.setContact(null);
        this.phoneNumbers.remove(phoneNumber);
    }

    public void merge(Contact contactToUpdate){
        name = contactToUpdate.getName() != null ? contactToUpdate.getName() : name;
        address = contactToUpdate.getAddress() != null ? contactToUpdate.getAddress() : address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) &&
               Objects.equals(name, contact.name) &&
               Objects.equals(address, contact.address) &&
               Objects.equals(phoneNumbers, contact.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phoneNumbers);
    }

    @Override
    public String toString() {
        return "Contact{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", phoneNumbers=" + phoneNumbers +
               '}';
    }
}
