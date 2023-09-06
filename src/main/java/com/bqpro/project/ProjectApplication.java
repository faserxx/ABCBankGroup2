package com.bqpro.project;

import com.bqpro.project.Model.Person;
import com.bqpro.project.Repository.PersonRepository;
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

	/*@Bean
	CommandLineRunner commandLineRunner(PersonRepository personRepository) {
		return args -> {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String[] a1 = new String[1];
			a1[0]="123 Main St";

			String[] a2 = new String[1];
			a1[0]="456 Elm St";


			String[] a3 = new String[1];
			a1[0]="789 Oak St";

			personRepository.save(new Person("John", "Doe", a1, new Date(dateFormat.parse("1990-01-01").getTime()), "555-1234", null));
			personRepository.save(new Person("Jane", "Smith", a2, new Date(dateFormat.parse("1985-06-15").getTime()), "555-5678", null));
			personRepository.save(new Person("Michael", "Johnson", a3, new Date(dateFormat.parse("1992-12-31").getTime()), "555-9876", null));
		};
	}*/

}
