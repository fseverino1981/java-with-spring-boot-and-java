package br.com.flavio.unittests.mapper.mocks;

import br.com.flavio.data.vo.v1.BookVO;
import br.com.flavio.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> Books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockEntity(i));
        }
        return Books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> Books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockVO(i));
        }
        return Books;
    }
    
    public Book mockEntity(Integer number) {
        Book Book = new Book();
        Book.setId(number.longValue());
        Book.setAuthor("Author " + number);
        Book.setLaunchDate(new Date());
        Book.setPrice(((number % 2)==0) ? 2D : 1D);
        Book.setTitle("Lord of the Rings " + number);
        return Book;
    }

    public BookVO mockVO(Integer number) {
        BookVO Book = new BookVO();
        Book.setKey(number.longValue());
        Book.setAuthor("Author " + number);
        Book.setLaunchDate(new java.util.Date());
        Book.setPrice(((number % 2)==0) ? 2D : 1D);
        Book.setTitle("Lord of the Rings " + number);
        return Book;
    }

}
