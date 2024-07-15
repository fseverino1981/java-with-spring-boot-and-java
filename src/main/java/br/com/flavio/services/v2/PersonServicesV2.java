package br.com.flavio.services.v2;

import br.com.flavio.data.vo.v2.PersonVOV2;
import br.com.flavio.exceptions.ResourceNotFoundException;
import br.com.flavio.mapper.Mapper;
import br.com.flavio.model.Person;
import br.com.flavio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServicesV2 {


    @Autowired
    PersonRepository repository;

    private Logger logger = Logger.getLogger(PersonServicesV2.class.getName());

    public PersonVOV2 findBYId(Long  id){

        logger.info("Find one person");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return Mapper.parseObject(entity, PersonVOV2.class);
    }

    public List<PersonVOV2> findAll(){
        logger.info("Finding all people!");
        return Mapper.parseListObjects(repository.findAll(),PersonVOV2.class);
    }
    public PersonVOV2 create(PersonVOV2 person){
        logger.info("Creating one person");

        Person entity = Mapper.parseObject(person, Person.class);
        PersonVOV2 vo = Mapper.parseObject(repository.save(entity), PersonVOV2.class);

        return vo;
    }
    public PersonVOV2 update(PersonVOV2 person){
        logger.info("Updating one person");


        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonVOV2 vo = Mapper.parseObject(repository.save(entity), PersonVOV2.class);

        return vo;
    }
    public void delete(Long  id){

        logger.info("Deleting one person");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }

}
