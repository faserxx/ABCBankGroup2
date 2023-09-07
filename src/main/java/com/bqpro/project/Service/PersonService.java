package com.bqpro.project.Service;

import com.bqpro.project.Model.Address;
import com.bqpro.project.Model.Person;
import com.bqpro.project.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public boolean findAddressByPerson(Person person,String address){
        boolean find = false;
        Iterator<Address> it = person.getAddresses().iterator();
        while(it.hasNext() && !find) {
            Address itaddress = it.next();
            String ittext= itaddress.getText().toLowerCase();
            if(ittext.contains(address.toLowerCase()))
            {
                find = true;
            }
        }

        return find;
    }


}
