package org.example.api;

import java.util.List;
import javax.ejb.Local;
import org.example.exception.IllegalJsonPropertyException;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;

@Local
public interface ContactServiceInterface {

    List<Contact> getAllContacts();

    Contact getContactById(Long id) throws ResourceNotFoundException;

    void deleteContactById(Long id) throws ResourceNotFoundException;

    Contact updateContact(Contact contact) throws ResourceNotFoundException, IllegalJsonPropertyException;

    Contact create(Contact contact) throws IllegalJsonPropertyException;
}
