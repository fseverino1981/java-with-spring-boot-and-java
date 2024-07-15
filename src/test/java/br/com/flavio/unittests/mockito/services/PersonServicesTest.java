package br.com.flavio.unittests.mockito.services;

import br.com.flavio.data.vo.v1.PersonVO;
import br.com.flavio.exceptions.RequiredObjectIsNullException;
import br.com.flavio.model.Person;
import br.com.flavio.repositories.PersonRepository;
import br.com.flavio.services.v1.PersonServices;
import br.com.flavio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test 1", result.getFirstName());
        assertEquals("Last Name Test 1", result.getLastName());
        assertEquals("Addres Test 1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        List<PersonVO> people = service.findAll();

        //validate about people list
        assertNotNull(people);
        assertEquals(14,people.size());

        //validate some index about people list
        PersonVO personOne = people.get(1);
        assertNotNull(personOne.getKey());
        assertNotNull(personOne.getLinks());
        assertTrue(personOne.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test 1", personOne.getFirstName());
        assertEquals("Last Name Test 1", personOne.getLastName());
        assertEquals("Addres Test 1", personOne.getAddress());
        assertEquals("Female", personOne.getGender());

        PersonVO personFour = people.get(4);
        assertNotNull(personFour.getKey());
        assertNotNull(personFour.getLinks());
        assertTrue(personFour.toString().contains("links: [</person/v1/4>;rel=\"self\"]"));
        assertEquals("First Name Test 4", personFour.getFirstName());
        assertEquals("Last Name Test 4", personFour.getLastName());
        assertEquals("Addres Test 4", personFour.getAddress());
        assertEquals("Male", personFour.getGender());


    }

    @Test
    void update() {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        when(repository.save(entity)).thenReturn((persisted));

        PersonVO result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test 1", result.getFirstName());
        assertEquals("Last Name Test 1", result.getLastName());
        assertEquals("Addres Test 1", result.getAddress());
        assertEquals("Female", result.getGender());

    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void create() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn((persisted));

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
        assertEquals("First Name Test 1", result.getFirstName());
        assertEquals("Last Name Test 1", result.getLastName());
        assertEquals("Addres Test 1", result.getAddress());
        assertEquals("Female", result.getGender());
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}