package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EmployeTest {
	
	//evaluation
	
	@Test
	public void testAugmentationSalaireSalaireNull() {
		//given
		Employe e1 = new Employe();
		e1.setSalaire(null);
		//when
		e1.augmenterSalaire(2.0);
		//then
		Assertions.assertThat(e1.getSalaire()).isEqualTo(null);
	}
	
	@Test
	public void testAugmentationSalaireSalaireAugmentationZero() {
		//given
		Employe e1 = new Employe();
		e1.setSalaire(1000.0);
		//when
		e1.augmenterSalaire(0.0);
		//then
		Assertions.assertThat(e1.getSalaire()).isEqualTo(1000.0);
	}
	
	@Test
	public void testAugmentationSalaireSalaireAugmentation() {
		//given
		Employe e1 = new Employe();
		e1.setSalaire(1000.0);
		//when
		e1.augmenterSalaire(1.0);
		//then
		Assertions.assertThat(e1.getSalaire()).isEqualTo(2000.0);
	}
	//Le TDD permet ma permis d'identifier les cas null avant de dev la fonction
	

	@ParameterizedTest
	@CsvSource({
		"-1, 8",
		"1, 11",
		"2, 10",
		"12, 12"
		})
	public void testGetNbRTT(Integer Annee, Integer result) {
		//given
		LocalDate dateRTT;
		if(Annee<0) {
			dateRTT = LocalDate.now().minusYears(Math.abs(Annee));
		}else if(Annee>0) {
			dateRTT = LocalDate.now().plusYears(Annee);
		}else {
			dateRTT = LocalDate.now();
		}
		Employe e1 = new Employe();
		e1.setTempsPartiel(1d);
		//when
		Integer resultat = e1.getNbRtt(dateRTT);
		//then
		Assertions.assertThat(resultat).isEqualTo(result);
	}
	
	
	//evaluation
	/*
	@Test
	public void testNombreAnneesAnciennetesMoins2 () {
		
		//Given : Employé dateEmbauche avec date 2 ans avant ajourd'hui -> 2 ans ancienneté
		Employe e1 = new Employe();
		e1.setDateEmbauche(LocalDate.now().minusYears(2));
		//When
		Integer tE1 = e1.getNombreAnneeAnciennete();
		//Then
		Assertions.assertThat(tE1).isEqualTo(2);
	}
	
	@Test
	public void testNombreAnneesAnciennetesTooBig() {
		//Given : Employé eumbauché dans le turfu
		Employe e1 = new Employe();
		e1.setDateEmbauche(LocalDate.now().plusYears(1));
		//When
		Integer tE1 = e1.getNombreAnneeAnciennete();
		//Then
		Assertions.assertThat(tE1).isEqualTo(0);
	}
	
	@Test
	public void testNombreAnneesAnciennetesToday() {
		//Given : Employé eumbauché ajd
		Employe e1 = new Employe();
		e1.setDateEmbauche(LocalDate.now());
		//When
		Integer tE1 = e1.getNombreAnneeAnciennete();
		//Then
		Assertions.assertThat(tE1).isEqualTo(0);
	}
	
	@Test
	public void testNombreAnneesAnciennetesUndefined() {
		//Given : Employé eumbauché null
		Employe e1 = new Employe();
		e1.setDateEmbauche(null);
		//When
		Integer tE1 = e1.getNombreAnneeAnciennete();
		//Then
		Assertions.assertThat(tE1).isEqualTo(0);
	}
	
	
	
	@ParameterizedTest
	@CsvSource({
		"'M12345', 2, 2, 1.0, 1900.0",
		"'M12345', 1, 2, 1.0, 1800.0",
		"'M12345', 0, 2, 1.0, 1700.0",
		"'T12345', 2, 2, 1.0, 2500.0",
		"'T12345', 1, 2, 1.0, 2400.0",
		"'T12345', 0, 2, 1.0, 2300.0",
		"'T12345', 0, 1, 1.0, 1000.0",
		"'T12345', 0, 1, 0.5, 500.0"
		})
	public void testCheckPrimeAnnuelle(String matricule, Integer dateEmbauche, Integer performance, Double tempsPartiel, Double Result) {
		Employe e1 = new Employe();
		e1.setMatricule(matricule);
		e1.setDateEmbauche(LocalDate.now().minusYears(dateEmbauche));
		e1.setPerformance(performance);
		e1.setTempsPartiel(tempsPartiel);
		Assertions.assertThat(e1.getPrimeAnnuelle()).isEqualTo(Result);
		
	}*/
	

}
