package org.example.dto;

import java.util.List;
import java.util.stream.Collectors;
import org.example.repo.Contact;
import org.modelmapper.ModelMapper;

public class ContactMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static ContactDto toDto(Contact contact) {
        return MODEL_MAPPER.map(contact, ContactDto.class);
    }

    public static List<ContactDto> toDto(List<Contact> contact) {
        return contact.stream().map(ContactMapper::toDto).collect(Collectors.toList());
    }

    public static Contact fromDto(ContactDto contactDto) {
        return MODEL_MAPPER.map(contactDto, Contact.class);
    }

}
