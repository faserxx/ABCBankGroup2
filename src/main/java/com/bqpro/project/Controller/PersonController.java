package com.bqpro.project.Controller;

import com.bqpro.project.Model.Address;
import com.bqpro.project.Model.Person;
import com.bqpro.project.Repository.AddressRepository;
import com.bqpro.project.Repository.PersonRepository;
import com.bqpro.project.Service.PersonService;
import com.bqpro.project.Utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /*@RequestMapping(value="/create", method=RequestMethod.POST,consumes={MediaType.MULTIPART_FORM_DATA_VALUE},
            produces=MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Person> create(@RequestPart(value="image", required=false) MultipartFile file, @RequestPart Person person) throws IOException {
        String a="";
            int index=file.getOriginalFilename().indexOf(".");
            String extension;
            extension="."+file.getOriginalFilename().substring(index+1);
            String nombreFoto= Calendar.getInstance().getTimeInMillis()+extension;
            FileUploadUtil.saveFile("person-images",nombreFoto,file);
            String absolute= Paths.get("person-images").toFile().getAbsolutePath()+ File.separator+nombreFoto;
           // a=a.concat(nombreFoto+",").trim();
        //a=a.substring(0,a.length()-1);
        person.setPersonalPhoto(absolute);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.savePerson(person));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }*/

    @RequestMapping(value="/create", method=RequestMethod.POST,consumes={MediaType.MULTIPART_FORM_DATA_VALUE},
            produces=MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Person> create(@RequestParam(value = "image", required = false) MultipartFile file,
                                          @RequestParam("firstName") String firstName,
                                          @RequestParam("secondName") String secondName,
                                          @RequestParam("dateOfBirth") Date dateOfBirth,
                                          @RequestParam("phoneNumber") String phoneNumber,
                                          @RequestParam("address") String[] addresses) throws IOException {

        Person person=new Person(firstName,secondName,dateOfBirth,phoneNumber);
        String a="";
        if(file!=null){
        int index=file.getOriginalFilename().indexOf(".");
        String extension;
        extension="."+file.getOriginalFilename().substring(index+1);
        String nombreFoto= Calendar.getInstance().getTimeInMillis()+extension;
        FileUploadUtil.saveFile("person-images",nombreFoto,file);
        String absolute= Paths.get("person-images").toFile().getAbsolutePath()+ File.separator+nombreFoto;
        // a=a.concat(nombreFoto+",").trim();
        //a=a.substring(0,a.length()-1);
        person.setPersonalPhoto(absolute);
        }
        try {
            Person personsave = personService.savePerson(person);
            if(personsave!=null)
            {
                List<Address> addressList = new ArrayList<Address>();
                for (String text : addresses) {
                    Address ad= new Address();
                    ad.setText(text);
                    ad.setPerson(personsave);
                    Address addressSave = addressRepository.save(ad);
                    addressList.add(addressSave);
                }
                personsave.setAddresses(addressList);
            }
                
            return ResponseEntity.status(HttpStatus.OK).body(personsave);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Optional<Person> existingPerson = personService.getPersonById(id);
        if (existingPerson.isPresent()) {
            person.setId(id);
            Person updatedPerson = personService.savePerson(person);
            List<Address> existingAddress = updatedPerson.getAddresses();
            List<Address> newsAddress = updatedPerson.getAddresses();
            
            for (Address add : person.getAddresses()) {
                Iterator<Address> it = existingAddress.iterator();
                boolean find = false;
                while(it.hasNext()) {
                    Address address = it.next();
                    if(add.getId()==address.getId())
                    {
                        find = true;
                        address.setText(add.getText());
                        addressRepository.save(address);
                    }
                }
                if(!find)
                {
                    Address ad= new Address();
                    ad.setText(add.getText());
                    ad.setPerson(updatedPerson);
                    Address addressSave = addressRepository.save(ad);
                    newsAddress.add(addressSave);
                    
                }
            }
            updatedPerson.setAddresses(newsAddress);

            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Optional<Person> existingPerson = personService.getPersonById(id);
        if (existingPerson.isPresent()) {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Person> searchPerson(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String secondName,
            @RequestParam(required = false) String addresses
    ) {
        Specification<Person> spec = Specification.where(null);

        if (firstName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("firstName"), firstName));
        }
        if (secondName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("secondName"), secondName));
        }
        /*if (addresses != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("addresses"), addresses));
        }*/

        List<Person> personlist= personRepository.findAll(spec);
        List<Person> newlist= new ArrayList<>();
        if (addresses != null)
        {
            for (Person person : personlist) {
                if(personService.findAddressByPerson(person, addresses))
                    newlist.add(person);
            }
        }
        

        return addresses!= null?newlist:personlist;
    }

    /*@GetMapping("/age-range")
    public ResponseEntity<List<Person>> getPersonsByAgeRange(@RequestParam("startAge") int startAge, @RequestParam("endAge") int endAge) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusYears(endAge);
        LocalDate endDate = currentDate.minusYears(startAge);

        Date sqlStartDate = Date.valueOf(startDate);
        Date sqlEndDate = Date.valueOf(endDate);

        List<Person> persons = personRepository.findByDateOfBirthBetween(sqlStartDate, sqlEndDate);
        return ResponseEntity.ok(persons);
    }*/

    @GetMapping("/age-range")
    public ResponseEntity<List<Person>> getPersonsByAgeRange(@RequestParam(value = "startAge", required = false) Integer startAge, @RequestParam(value = "endAge", required = false) Integer endAge) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (startAge != null && endAge != null) {
            startDate = currentDate.minusYears(endAge);
            endDate = currentDate.minusYears(startAge);
        } else if (startAge != null) {
            startDate = currentDate.minusYears(200);
            endDate = currentDate.minusYears(startAge);
        } else if (endAge != null) {
            startDate = currentDate.minusYears(endAge);
            endDate = currentDate;
        }

        if (startDate != null && endDate != null) {
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);

            List<Person> persons = personRepository.findByDateOfBirthBetween(sqlStartDate, sqlEndDate);
            return ResponseEntity.ok(persons);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}

