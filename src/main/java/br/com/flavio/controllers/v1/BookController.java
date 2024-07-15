package br.com.flavio.controllers.v1;

import br.com.flavio.data.vo.v1.BookVO;
import br.com.flavio.model.Book;
import br.com.flavio.services.v1.BookServices;
import br.com.flavio.utils.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Books", description = "Service to managing Books")
public class BookController {

    @Autowired
    private BookServices bookServices;

    @GetMapping(value="/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a Book", description="Finds a Book",
     tags = {"Books"},
     responses = {@ApiResponse(description = "Success", responseCode = "200",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
     })
    public BookVO findByID(@PathVariable(value = "id") Long id){
        return bookServices.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Books", description = "Finds All Books",
                tags = {"Books"},
                responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = BookVO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                })
    public List<BookVO> findAll(){
        List<BookVO> vo = bookServices.findAll();
        return vo;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds a Book", description = "Adds a Book", tags = {"Books"},
                responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                     content = @Content(schema = @Schema(implementation = BookVO.class))),
                    @ApiResponse(description = "Bad Resquest", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                })
    public BookVO create(@RequestBody BookVO bookVO){
        return bookServices.create(bookVO);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a Book", description = "Updates a Book", tags = {"Books"},
                responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)

                })
    public BookVO update(@RequestBody BookVO bookVO){
        return bookServices.update(bookVO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a book", description="Deletes a book", tags ={"Books"},
                responses = {
                    @ApiResponse(description = "No Content", responseCode = "204",content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500",content = @Content)
                })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        bookServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
