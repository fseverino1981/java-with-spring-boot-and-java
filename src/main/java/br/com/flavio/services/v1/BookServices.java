package br.com.flavio.services.v1;

import br.com.flavio.controllers.v1.BookController;
import br.com.flavio.data.vo.v1.BookVO;
import br.com.flavio.exceptions.RequiredObjectIsNullException;
import br.com.flavio.exceptions.ResourceNotFoundException;
import br.com.flavio.mapper.Mapper;
import br.com.flavio.model.Book;
import br.com.flavio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    public BookVO findById(Long id){

        logger.info("Finding a book");

        Book entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records not found for this ID"));
        BookVO vo = Mapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(id)).withSelfRel());
        return vo;
    }

    public List<BookVO> findAll(){
        logger.info("Finding all books!");

        List<BookVO> vo = Mapper.parseListObjects(repository.findAll(), BookVO.class);
        vo.stream().forEach((p -> p.add(linkTo(methodOn(BookController.class).findByID(p.getKey())).withSelfRel())));
        return vo;
    }

    public BookVO create(BookVO book){

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book");

        Book entity = Mapper.parseObject(book, Book.class);
        BookVO vo = Mapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(vo.getKey())).withSelfRel());

        return vo;
    }

    public BookVO update(BookVO book){
        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one book");

        Book entity = repository.findById(book.getKey()).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setId(book.getKey());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        BookVO vo = Mapper.parseObject(repository.save(entity),BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findByID(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id){

        logger.info("Deleting a book");

        Book entity = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);

    }
}
