package org.example.test;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.example.api.ContactServiceInterfaceRemote;
import org.example.exception.IllegalJsonPropertyException;
import org.example.exception.ResourceNotFoundException;
import org.example.repo.Contact;
import org.example.repo.PhoneNumber;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test
public class TestSuit {

    Context ctx;
    ContactServiceInterfaceRemote contactServiceInterfaceRemote;

    @BeforeClass
    @Parameters({ "jndi-name" })
    public void init(String jndi) throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
        jndiProps.put("jboss.naming.client.ejb.context", true);
        this.ctx = new InitialContext(jndiProps);
        this.contactServiceInterfaceRemote = (ContactServiceInterfaceRemote)ctx.lookup(jndi);

    }

    @Test(groups = {"get-contact"}, dependsOnGroups = "create.*")
    public void getAllContacts()  {
        List<Contact> allContacts = this.contactServiceInterfaceRemote.getAllContacts();

        Assert.assertFalse(allContacts.isEmpty());
    }

    @Test(groups = {"get-contact"}, dependsOnGroups = "create.*")
    public void getMissingContact()  {
        Assert.expectThrows(ResourceNotFoundException.class, () -> contactServiceInterfaceRemote.getContactById(-1L));
    }

    @Test(groups = {"get-contact"}, dependsOnGroups = "create.*")
    public void getContactById() throws IllegalJsonPropertyException, ResourceNotFoundException {
        Contact contact = getContact();

        Contact newContact = contactServiceInterfaceRemote.create(contact);
        Assert.assertNotNull(contactServiceInterfaceRemote.getContactById(newContact.getId()));
    }


    @Test(groups = {"create-contact"})
    public void createContact() throws IllegalJsonPropertyException {
        Contact contact = getContact();

        Contact newContact = contactServiceInterfaceRemote.create(contact);
        Assert.assertNotNull(newContact.getId());
        Assert.assertTrue(newContact.getPhoneNumbers().stream().map(PhoneNumber::getId).noneMatch(Objects::isNull));
    }

    @Test(groups = {"create-contact"})
    public void createContactWithoutName() {
        Contact contact = getContact();
        contact.setName(null);
        Assert.expectThrows(IllegalJsonPropertyException.class,() -> contactServiceInterfaceRemote.create(contact));
    }

    @Test(groups = {"create-contact"})
    public void createContactWithoutAddress() {
        Contact contact = getContact();
        contact.setAddress(null);
        Assert.expectThrows(IllegalJsonPropertyException.class,() -> contactServiceInterfaceRemote.create(contact));
    }

    @Test(groups = {"create-contact"})
    public void createContactInvalidNameSize() {
        Contact contact = getContact();
        contact.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assert.expectThrows(IllegalJsonPropertyException.class,() -> contactServiceInterfaceRemote.create(contact));
    }

    @Test(groups = {"create-contact"})
    public void createContactEmptyName() {
        Contact contact = getContact();
        contact.setName("");
        Assert.expectThrows(IllegalJsonPropertyException.class,() -> contactServiceInterfaceRemote.create(contact));
    }

    @Test(groups = {"update-contact"})
    public void updateContact() throws IllegalJsonPropertyException, ResourceNotFoundException {
        Contact contact = getContact();

        Contact newContact = contactServiceInterfaceRemote.create(contact);
        newContact.setName("Updated name");
        newContact.setAddress("Updated address");
        Contact updated = contactServiceInterfaceRemote.updateContact(newContact);
        Assert.assertEquals(updated.getName(), "Updated name");
        Assert.assertEquals(updated.getAddress(), "Updated address");
    }

    @Test(groups = {"update-contact"})
    public void updateContactNumber() throws IllegalJsonPropertyException, ResourceNotFoundException {
        Contact contact = getContact();

        Contact newContact = contactServiceInterfaceRemote.create(contact);
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setDescription("Home number");
        phoneNumber.setNumber("+351239456123");
        newContact.getPhoneNumbers().add(phoneNumber);
        Contact updated = contactServiceInterfaceRemote.updateContact(newContact);
        Assert.assertEquals(updated.getPhoneNumbers().size(), 3);

    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setName("Test contact");
        contact.setAddress("Test contact address");
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setDescription("Personal number");
        phoneNumber.setNumber("+351465415465");
        PhoneNumber workNumber = new PhoneNumber();
        workNumber.setDescription("Work number");
        workNumber.setNumber("+351987654231");
        contact.setPhoneNumbers(Stream.of(phoneNumber, workNumber).collect(Collectors.toSet()));
        return contact;
    }

}
