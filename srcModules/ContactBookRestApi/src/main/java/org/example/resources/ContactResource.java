package org.example.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.example.api.ContactServiceInterface;
import org.example.dto.ContactDto;
import org.example.dto.ContactMapper;
import org.example.dto.PhoneNumberDto;
import org.example.repo.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/contact")
public class ContactResource {

    private static final Logger logger = LoggerFactory.getLogger(ContactResource.class);

    @EJB
    ContactServiceInterface contactService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        logger.debug("Get all");
        return Response.status(HttpStatus.SC_OK).entity(ContactMapper.toDto(contactService.getAllContacts())).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        return Response.status(HttpStatus.SC_OK).entity(ContactMapper.toDto(contactService.getContactById(id))).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam(value = "id") Long id) {
        contactService.deleteContactById(id);
        return Response.status(HttpStatus.SC_NO_CONTENT).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ContactDto contactDto) {
        Contact contact = ContactMapper.fromDto(contactDto);
        return Response.status(HttpStatus.SC_CREATED).entity(ContactMapper.toDto(contactService.create(contact))).build();
    }


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam(value = "id") Long id, ContactDto contactDto) {
        contactDto.setId(id);
        Contact contact = ContactMapper.fromDto(contactDto);
        return Response.status(HttpStatus.SC_OK).entity(ContactMapper.toDto(contactService.updateContact(contact))).build();
    }


}
