package com.ipiecoles.java.java350.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.service.EmployeService;

@ExtendWith(SpringExtension.class) //Junit 5 
@SpringBootTest
public class EmployeRepositoryTest {

	@Autowired
	EmployeRepository employeRepository;
	
	EmployeService employeService;
	
	@BeforeEach
	public void setup() {
		employeRepository.deleteAll();
	}
	
	
	//evaluation
	@Test
	public void testAvgPerformanceWhereMatriculeStartsWith() {
		//given
		Employe emp1 = new Employe();
		emp1.setMatricule("C12345");
		emp1.setPerformance(10);
		employeRepository.save(emp1);
		Employe emp2 = new Employe();
		emp2.setMatricule("C33333");
		emp2.setPerformance(5);
		employeRepository.save(emp2);
		//when
		Double result = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
		//then
		Assertions.assertThat(result).isEqualTo(7.5);
	}
	
	
	@Test
	public void testCalculPerformanceCommercialIntegrated() throws EmployeException {
		//given
		Employe e1 = new Employe();
		e1.setMatricule("C12345");
		e1.setPerformance(5);
		employeRepository.save(e1);
		//when
		employeService.calculPerformanceCommercial("C12345", 85l, 100l);
		//then
		Assertions.assertThat(employeRepository.findByMatricule("C12345")).isEqualTo(4);
		
	}
	
	
	
	
	
	//evaluation
	
	
	/*
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
	}*/

	
	
}
