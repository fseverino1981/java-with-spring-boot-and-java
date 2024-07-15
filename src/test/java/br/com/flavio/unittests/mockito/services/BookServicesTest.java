package br.com.flavio.unittests.mockito.services;

import br.com.flavio.data.vo.v1.BookVO;
import br.com.flavio.exceptions.RequiredObjectIsNullException;
import br.com.flavio.model.Book;
import br.com.flavio.repositories.BookRepository;
import br.com.flavio.services.v1.BookServices;
import br.com.flavio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book entity = input.mockEntity(1);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author 1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Lord of the Rings 1", result.getTitle());
    }

    @Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        List<BookVO> people = service.findAll();

        //validate about people list
        assertNotNull(people);
        assertEquals(14,people.size());

        //validate some index about people list
        BookVO bookOne = people.get(1);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertTrue(bookOne.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author 1", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals(1D, bookOne.getPrice());
        assertEquals("Lord of the Rings 1", bookOne.getTitle());

        BookVO bookFour = people.get(4);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());
        assertTrue(bookFour.toString().contains("links: [</book/v1/4>;rel=\"self\"]"));
        assertEquals("Author 4", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals(2D, bookFour.getPrice());
        assertEquals("Lord of the Rings 4", bookFour.getTitle());


    }

    @Test
    void update() {
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        when(repository.save(entity)).thenReturn((persisted));

        BookVO result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author 1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Lord of the Rings 1", result.getTitle());

    }

    @Test
    void createWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Disabled
    void create() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn((persisted));

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
        assertEquals("Author 1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1D, result.getPrice());
        assertEquals("Lord of the Rings 1", result.getTitle());
    }

    @Test
    void delete() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}