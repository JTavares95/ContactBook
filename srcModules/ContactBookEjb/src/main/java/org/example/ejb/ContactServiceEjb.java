package org.example.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.example.api.ContactServiceInterface;
import org.example.api.ContactServiceInterfaceRemote;
import org.example.api.ContactServiceJpaInterface;
import org.example.exception.IllegalJsonPropertyException;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;
import org.example.repo.PhoneNumber;

@Stateless(mappedName = "MY_EJB", name = "EJB_TESTE")
public class ContactServiceEjb implements ContactServiceInterface, ContactServiceInterfaceRemote {

    public static final int STRING_MAX_SIZE = 255;
    public static final int STRING_MIN_SIZE = 0;

    @EJB
    private ContactServiceJpaInterface contactServiceJpaInterface;

    @Override
    public List<Contact> getAllContacts() {
        return contactServiceJpaInterface.getAllContacts();
    }

    @Override
    public Contact getContactById(Long id) throws ResourceNotFoundException {
        return contactServiceJpaInterface.getContactById(id);
    }

    @Override
    public void deleteContactById(Long id) throws ResourceNotFoundException {
        contactServiceJpaInterface.deleteContactById(id);
    }

    @Override
    public Contact updateContact(Contact updatedContact) throws ResourceNotFoundException, IllegalJsonPropertyException {
        validateOnUpdate(updatedContact);
        Contact contact = getContactById(updatedContact.getId());
        if (updatedContact.getPhoneNumbers() != null) {
            Set<PhoneNumber> oldPhoneNumbers = new HashSet<>(contact.getPhoneNumbers());
            for (PhoneNumber phoneNumber : oldPhoneNumbers) {
                contact.removePhoneNumber(phoneNumber);
            }
            updatedContact.getPhoneNumbers().forEach(contact::addPhoneNumber);
        }
        contact.merge(updatedContact);
        return contactServiceJpaInterface.updateContact(contact);
    }

    @Override
    public Contact create(Contact contact) throws IllegalJsonPropertyException {
        validateContact(contact);
        if (contact.getPhoneNumbers() != null) {
            Set<PhoneNumber> phoneNumbers = contact.getPhoneNumbers();
            phoneNumbers.forEach(contact::addPhoneNumber);
        }
        return contactServiceJpaInterface.create(contact);
    }

    private void validateContact(Contact contact) throws IllegalJsonPropertyException {
        if (contact.getName() == null || contact.getName().isEmpty()) {
            throw new IllegalJsonPropertyException("The attribute 'name' is mandatory");
        }
        if (contact.getName().length() > STRING_MAX_SIZE) {
            throw new IllegalJsonPropertyException(String.format("The size of the attribute 'name' must be lower than %d", STRING_MAX_SIZE));
        }
        if (contact.getAddress() == null || contact.getAddress().isEmpty()) {
            throw new IllegalJsonPropertyException("The attribute 'address' is mandatory");
        }
        if (contact.getAddress().length() > STRING_MAX_SIZE) {
            throw new IllegalJsonPropertyException(String.format("The size of the attribute 'address' must be lower than %d", STRING_MAX_SIZE));
        }
        if (contact.getPhoneNumbers() != null) {
            validatePhoneNumber(contact.getPhoneNumbers());
        }
    }

    private void validateOnUpdate(Contact contact) throws IllegalJsonPropertyException {
        if (contact.getName() != null && (contact.getName().length() > STRING_MAX_SIZE || contact.getName().isEmpty())) {
            throw new IllegalJsonPropertyException(String.format("The size of the attribute 'address' must be bigger than %d and lower than %d", STRING_MIN_SIZE, STRING_MAX_SIZE));
        }
        if (contact.getAddress() != null && (contact.getAddress().length() > STRING_MAX_SIZE || contact.getAddress().isEmpty())) {
            throw new IllegalJsonPropertyException(String.format("The size of the attribute 'address' must be bigger than %d and lower than %d", STRING_MIN_SIZE, STRING_MAX_SIZE));
        }
    }

    private void validatePhoneNumber(Set<PhoneNumber> phoneNumbers) throws IllegalJsonPropertyException {
        for (PhoneNumber phoneNumber : phoneNumbers) {
            if (phoneNumber.getNumber() == null || phoneNumber.getNumber().isEmpty()) {
                throw new IllegalJsonPropertyException("The attribute 'number' is mandatory");
            }
            if (phoneNumber.getNumber().length() > STRING_MAX_SIZE) {
                throw new IllegalJsonPropertyException(String.format("The size of the attribute 'number' must be lower than %d", STRING_MAX_SIZE));
            }
            if (phoneNumber.getDescription() != null && (phoneNumber.getDescription().length() > STRING_MAX_SIZE || phoneNumber.getDescription().isEmpty())) {
                throw new IllegalJsonPropertyException(
                        String.format("The size of the attribute 'description' must be bigger than %d and lower than %d", STRING_MIN_SIZE, STRING_MAX_SIZE));
            }
        }
    }
}
