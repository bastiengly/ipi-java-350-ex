package com.ipiecoles.java.java350.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.model.Employe;

@ExtendWith(SpringExtension.class) //Junit 5 
@SpringBootTest
public class EmployeRepositoryTest {

	@Autowired
	EmployeRepository employeRepository;
	
	@BeforeEach
	@AfterAll
	public void setup() {
		employeRepository.deleteAll();
	}
	
	@Test
	public void testFindMaxMatriculeEmpty() {
		//given

		//when
		String result = employeRepository.findLastMatricule();
		//then
		Assertions.assertThat(result).isNull();
	}
	
	@Test
	public void testFindMaxMatricule() {
		//given
		Employe emp1 = new Employe();
		emp1.setMatricule("T12345");
		employeRepository.save(emp1);
		Employe emp2 = new Employe();
		emp2.setMatricule("M33333");
		employeRepository.save(emp2);
		//when
		String result = employeRepository.findLastMatricule();
		//then
		Assertions.assertThat(result).isEqualTo("33333");
	}

	
	
}
