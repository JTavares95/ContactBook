package org.example.api;

import java.util.List;
import javax.ejb.Local;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;

/**
 * This Bean is responsible to persist the entities into the database
 * No validation is done here
 */

@Local
public interface ContactServiceJpaInterface {

    /**
     * Gets all the contacts registered in the database
     * @return List of all contacts registered in the database org.example.model.Contact
     */
    List<Contact> getAllContacts();

    /**
     * Get a specific contact
     * @param id - Id of the contact
     * @return Contact with the given id
     * @throws ResourceNotFoundException - exception thrown in case there is no record with the given id
     */
    Contact getContactById(Long id) throws ResourceNotFoundException;

    /**
     * Delete a contact with a given id
     * @param id - contact id to be removed
     * @throws ResourceNotFoundException - exception thrown in case there is no record with the given id
     */
    void deleteContactById(Long id) throws ResourceNotFoundException;

    /**
     * Update a contact info, all the attributes present are replaced
     * Notes:
     *  - To remove all the phone numbers, an empty list must be provided
     * @param contact - Contact with information to update
     * @return - Contact with the updated information
     */
    Contact updateContact(Contact contact);

    /**
     * Create a new contact
     * @param contact
     * @return - Created contact
     */
    Contact create(Contact contact);
}
