package br.com.flavio.services.v1;

import br.com.flavio.controllers.v1.PersonController;
import br.com.flavio.data.vo.v1.PersonVO;
import br.com.flavio.exceptions.RequiredObjectIsNullException;
import br.com.flavio.exceptions.ResourceNotFoundException;
import br.com.flavio.mapper.Mapper;
import br.com.flavio.model.Person;
import br.com.flavio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {


    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public PersonVO findById(Long  id){

        logger.info("Find one person");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        PersonVO vo = Mapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVO> findAll(){
        logger.info("Finding all people!");
        List<PersonVO> vo = Mapper.parseListObjects(repository.findAll(),PersonVO.class);
        vo.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return vo;
    }
    public PersonVO create(PersonVO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person");

        Person entity = Mapper.parseObject(person, Person.class);
        PersonVO vo = Mapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
    public PersonVO update(PersonVO person){

        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person");


        Person entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonVO vo = Mapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
    public void delete(Long  id){

        logger.info("Deleting one person");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }

}
