package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeTest {

	@Test
	public void testCheckBadNombreAnneeAnciennete () {
		//Given
		Employe e1 = new Employe();
		e1.setDateEmbauche(LocalDate.parse("2016-08-20"));
		
		//When
		Integer tE1 = e1.getNombreAnneeAnciennete();

		
		//Then
		Assertions.assertThat(tE1).isPositive();

	}

}
