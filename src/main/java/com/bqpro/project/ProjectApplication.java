package com.bqpro.project;

import com.bqpro.project.Model.Address;
import com.bqpro.project.Model.Person;
import com.bqpro.project.Repository.PersonRepository;
import com.bqpro.project.Repository.AddressRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PersonRepository personRepository, AddressRepository addressRepository) {
		return args -> {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


			Person p1=new Person("John", "Doe", new Date(dateFormat.parse("1990-01-01").getTime()), "555-1234", null);
			Person p2=new Person("Jane", "Smith", new Date(dateFormat.parse("1985-06-15").getTime()), "555-5678", null);
			Person p3=new Person("Michael", "Johnson", new Date(dateFormat.parse("1992-12-31").getTime()), "555-9876", null);

			personRepository.save(p1);
			personRepository.save(p2);
			personRepository.save(p3);

			Address a1= new Address();
			a1.setText("123 Main St");
			Address a11= new Address();
			a11.setText("456 Miami St");
			a1.setPerson(p1);
			a11.setPerson(p1);
			
			Address a2= new Address();
			a2.setText("456 Elm St");
			a2.setPerson(p2);

			Address a3= new Address();
			a3.setText("789 Oak St");
			a3.setPerson(p3);

			addressRepository.save(a1);
			addressRepository.save(a11);
			addressRepository.save(a2);
			addressRepository.save(a3);
		};
	}

}
