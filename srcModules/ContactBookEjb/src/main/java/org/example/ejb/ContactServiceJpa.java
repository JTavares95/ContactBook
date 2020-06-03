package org.example.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.example.api.ContactServiceJpaInterface;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;

@Stateless
public class ContactServiceJpa implements ContactServiceJpaInterface {

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
    public Contact getContactById(Long id) throws ResourceNotFoundException {
        EntityManager entityManager = getEntityManager();
        Contact contact = entityManager.find(Contact.class, id);
        if(contact == null) {
            throw new ResourceNotFoundException(String.format("Couldn't find a contact with id %d", id));
        }
        return contact;
    }

    @Override
    public void deleteContactById(Long id) throws ResourceNotFoundException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Contact contact = entityManager.find(Contact.class, id);
        if(contact == null) {
            throw new ResourceNotFoundException(String.format("Couldn't find a contact with id %d", id));
        }
        entityManager.remove(contact);
        transaction.commit();
    }

    @Override
    public Contact updateContact(Contact contact) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Contact updatedContact = entityManager.merge(contact);
        transaction.commit();
        return updatedContact;
    }

    @Override
    public Contact create(Contact contact) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(contact);
        transaction.commit();
        return contact;
    }
}
