package com.learning.courses.service;

import com.learning.courses.dto.ContactDTO;
import com.learning.courses.dto.CreateContactDTO;
import com.learning.courses.exception.EntityNotFoundException;
import com.learning.courses.exception.InvalidRoleException;
import com.learning.courses.mapper.ContactMapper;
import com.learning.courses.model.Contact;
import com.learning.courses.model.Person;
import com.learning.courses.model.enums.Role;
import com.learning.courses.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final PersonService personService;


    private Person getValidatedStudent(Long personId) {
        Person person = personService.getPersonEntity(personId);
        if (person.getRole() != Role.STUDENT) {
            throw new InvalidRoleException(personId, Role.STUDENT, person.getRole());
        }
        return person;
    }

    @Transactional
    public Long createContact(Long personId, CreateContactDTO createContactDTO) {
        Person person = getValidatedStudent(personId);
        Contact contact = contactMapper.toEntity(createContactDTO);
        contact.setPerson(person);
        return contactRepository.save(contact).getId();
    }

    @Transactional(readOnly = true)
    public List<ContactDTO> getContacts(Long personId) {
        getValidatedStudent(personId);
        return contactRepository.findAllByPersonId(personId).stream()
                .map(contactMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContactDTO getContact(Long personId, Long contactId) {
        getValidatedStudent(personId);
        return contactRepository.findById(contactId)
                .filter(contact -> contact.getPerson().getId().equals(personId))
                .map(contactMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(contactId, Contact.class.getSimpleName()));
    }

    @Transactional
    public ContactDTO updateContact(Long personId, Long contactId, CreateContactDTO updatedContact) {
        getValidatedStudent(personId);
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getPerson().getId().equals(personId))
                .orElseThrow(() -> new EntityNotFoundException(contactId, Contact.class.getSimpleName()));

        contact.setEmail(updatedContact.getEmail());
        contact.setAddress(updatedContact.getAddress());
        contact.setPhoneNumber(updatedContact.getPhoneNumber());

        return contactMapper.toDTO(contactRepository.save(contact));
    }

    @Transactional
    public void deleteContact(Long personId, Long contactId) {
        getValidatedStudent(personId);
        Contact contact = contactRepository.findById(contactId)
                .filter(c -> c.getPerson().getId().equals(personId))
                .orElseThrow(() -> new EntityNotFoundException(contactId, Contact.class.getSimpleName()));

        contactRepository.delete(contact);
    }
}