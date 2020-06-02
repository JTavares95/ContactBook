package org.example.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.example.api.ContactServiceInterface;
import org.example.repo.Contact;
import org.example.repo.PhoneNumber;

@Stateless
public class ContactServiceEjb implements ContactServiceInterface {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");


    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }


    @Override
    public List<Contact> getAllContacts() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> rootEntry = cq.from(Contact.class);
        CriteriaQuery<Contact> all = cq.select(rootEntry);
        TypedQuery<Contact> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Contact getContactById(Long id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(Contact.class, id);
    }

    @Override
    public void deleteContactById(Long id) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Contact.class, id));
        transaction.commit();
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        EntityManager entityManager = getEntityManager();
        Contact contact = entityManager.find(Contact.class, updatedContact.getId());
        if (updatedContact.getPhoneNumbers() != null) {
            Set<PhoneNumber> oldPhoneNumbers = new HashSet<>(contact.getPhoneNumbers());
            for (PhoneNumber phoneNumber : oldPhoneNumbers) {
                contact.removePhoneNumber(phoneNumber);
            }
            updatedContact.getPhoneNumbers().forEach(contact::addPhoneNumber);
        }
        contact.merge(updatedContact);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(contact);
        transaction.commit();
        return contact;
    }

    @Override
    public Contact create(Contact contact) {
        if (contact.getPhoneNumbers() != null) {
            Set<PhoneNumber> phoneNumbers = contact.getPhoneNumbers();
            phoneNumbers.forEach(contact::addPhoneNumber);
        }
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(contact);
        transaction.commit();
        return contact;
    }
}
