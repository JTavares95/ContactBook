package org.example.api;

import java.util.List;
import javax.ejb.Local;
import org.example.repo.Contact;

@Local
public interface ContactServiceInterface {

    List<Contact> getAllContacts();

    Contact getContactById(Long id);

    void deleteContactById(Long id);

    Contact updateContact(Contact contact);

    Contact create(Contact contact);
}
