package com.ipiecoles.java.java350.service;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
		
	}

}
