package com.ipiecoles.java.java350.service;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {
	
	@Mock
	EmployeRepository empRepo;
	@InjectMocks
	EmployeService empService;
	
	@Autowired
	EmployeRepository empRepoNotMocked;
	
	EmployeService empServiceNotMocked;
	
	//evaluation
	

	
	@Test
	public void testCalculPerformanceCommercialMatNull() {
		//given
		String matricule=null;
		Long caTraite=25l; 
		Long objectifCa=25l;
		//when
		try {
			empService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
			Assertions.fail("YA UNE ERREUR ICI NORMALEMENT");
		}catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule ne peut être null et doit commencer par un C !");
		}	
	}
	
	@Test
	public void testCalculPerformanceCommercialTraiteNull() {
		//given
		String matricule="T12345";
		Long caTraite=null; 
		Long objectifCa=25l;
		//when
		try {
			empService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
			Assertions.fail("YA UNE ERREUR ICI NORMALEMENT");
		}catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le chiffre d'affaire traité ne peut être négatif ou null !");
		}	
	}
	
	@Test
	public void testCalculPerformanceCommercialObjectifNull() {
		//given
		String matricule="T12345";
		Long caTraite=25l; 
		Long objectifCa=null;
		//when
		try {
			empService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
			Assertions.fail("YA UNE ERREUR ICI NORMALEMENT");
		}catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("L'objectif de chiffre d'affaire ne peut être négatif ou null !");
		}	
	}
	
	@Test
	public void testCalculPerformanceCommercialMatriculeNotFound() {
		//given
		empRepo.deleteAll();
		String matricule="C12345";
		Long caTraite=25l; 
		Long objectifCa=25l;
		//when
		try {
			empService.calculPerformanceCommercial(matricule, caTraite, objectifCa);
			Assertions.fail("YA UNE ERREUR ICI NORMALEMENT");
		}catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Le matricule C12345 n'existe pas !");
		}	
	}
	

	@ParameterizedTest
	@CsvSource({ //Tests des 4 cas, "a,b,c,d" c est le résultat attendu / d est le resultat de avgPerformanceWhereMatriculeStartsWith("C")
		"85,100,4,0",		
		"100,100,6,0",		
		"110,100,7,0",		
		"130,100,10,0",		
		
		"85,100,3,10",	 	
		"100,100,5,10",		
		"110,100,6,10",		
		"130,100,9,10",		
		})
	public void testCalculPerformanceCommercialParams(Long caTraite, Long objectifCa, Integer result, Double avgPerf) throws EmployeException {
		//given
		Employe e1 = new Employe();
		e1.setPerformance(5);
		Mockito.when(empRepo.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(avgPerf);
		Mockito.when(empRepo.findByMatricule("C12345")).thenReturn(e1);
		//when
		empService.calculPerformanceCommercial("C12345", caTraite, objectifCa);
		//then
		ArgumentCaptor<Employe> empCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(empRepo, Mockito.times(1)).save(empCaptor.capture());
		Assertions.assertThat(empCaptor.getValue().getPerformance()).isEqualTo(result);
	}
	
	
	//Test Integration Bloquer car le Repository instancié dans EmployeService n'est pas le Repository "SpringBootTest"
	//La création de base ne fonctionne pas
	
	
	
	
	
	//evaluation
	
	
	/*
	@Test
	public void EmbaucheEmployeTest() throws EntityExistsException, EmployeException {
		//given
		empRepo.deleteAll();
		Mockito.when(empRepo.findLastMatricule()).thenReturn("12345");
		Mockito.when(empRepo.findByMatricule("C12346")).thenReturn(null);
		//when
		empService.embaucheEmploye("Jean", "Jean", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
		//then
		ArgumentCaptor<Employe> empCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(empRepo, Mockito.times(1)).save(empCaptor.capture());
		Assertions.assertThat(empCaptor.getValue().getMatricule()).isEqualTo("C12346");
		Assertions.assertThat(empCaptor.getValue().getNom()).isEqualTo("Jean");
		Assertions.assertThat(empCaptor.getValue().getPrenom()).isEqualTo("Jean");
		Assertions.assertThat(empCaptor.getValue().getTempsPartiel()).isEqualTo(1.0);
		Assertions.assertThat(empCaptor.getValue().getDateEmbauche().getYear()).isEqualTo(2020);
		Assertions.assertThat(empCaptor.getValue().getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		
		Assertions.assertThat(empCaptor.getValue().getSalaire()).isEqualTo(2129.71);
	}
	
	
	@Test
	public void EmbaucheEmployeTestMatriculeNull() throws EntityExistsException, EmployeException {
		//given
		empRepo.deleteAll();
		Mockito.when(empRepo.findLastMatricule()).thenReturn(null);
		Mockito.when(empRepo.findByMatricule("C00001")).thenReturn(null);
		//when
		empService.embaucheEmploye("Jean", "Jean", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
		//then
		ArgumentCaptor<Employe> empCaptor = ArgumentCaptor.forClass(Employe.class);
		Mockito.verify(empRepo, Mockito.times(1)).save(empCaptor.capture());
		Assertions.assertThat(empCaptor.getValue().getMatricule()).isEqualTo("C00001");
		Assertions.assertThat(empCaptor.getValue().getNom()).isEqualTo("Jean");
		Assertions.assertThat(empCaptor.getValue().getPrenom()).isEqualTo("Jean");
		Assertions.assertThat(empCaptor.getValue().getTempsPartiel()).isEqualTo(1.0);
		Assertions.assertThat(empCaptor.getValue().getDateEmbauche().getYear()).isEqualTo(2020);
		Assertions.assertThat(empCaptor.getValue().getPerformance()).isEqualTo(Entreprise.PERFORMANCE_BASE);
		
		Assertions.assertThat(empCaptor.getValue().getSalaire()).isEqualTo(2129.71);
	}
	

	@Test
	public void TestLimiteMatriculeEmbaucheEmploye() throws EntityExistsException, EmployeException {
		//given
		empRepo.deleteAll();
		Mockito.when(empRepo.findLastMatricule()).thenReturn("99999");
		//when
		try {
			empService.embaucheEmploye("Jean", "Jean", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
			Assertions.fail("YA UNE ERREUR ICI NORMALEMENT");
		}catch(Exception e) {
			//then
			Assertions.assertThat(e).isInstanceOf(EmployeException.class);
			Assertions.assertThat(e.getMessage()).isEqualTo("Limite des 100000 matricules atteinte !");
		}
		
	}*/

}
