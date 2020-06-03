package org.example.api;

import java.util.List;
import javax.ejb.Local;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;

@Local
public interface ContactServiceJpaInterface {

    List<Contact> getAllContacts();

    Contact getContactById(Long id) throws ResourceNotFoundException;

    void deleteContactById(Long id) throws ResourceNotFoundException;

    Contact updateContact(Contact contact);

    Contact create(Contact contact);
}
