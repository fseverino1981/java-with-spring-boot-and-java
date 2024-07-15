package br.com.flavio.controllers.v2;

import br.com.flavio.data.vo.v2.PersonVOV2;
import br.com.flavio.services.v2.PersonServicesV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person/v2")
public class PersonControllerV2 {

    @Autowired
    PersonServicesV2 personServices;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 findById(@PathVariable(value = "id") Long id){
        return personServices.findBYId(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVOV2> findAll(){
        return personServices.findAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 create(@RequestBody PersonVOV2 personVOV2){
        return personServices.create(personVOV2);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 udpate(@RequestBody PersonVOV2 personVOV2){
        return personServices.update(personVOV2);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        personServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
