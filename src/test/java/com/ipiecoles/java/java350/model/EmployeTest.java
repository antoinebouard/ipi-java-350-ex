package com.ipiecoles.java.java350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());
        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus1(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(1L));
        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();
        //Then
        Assertions.assertEquals(1, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus1(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(1L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest(name = "Matricule {0}, performance {1}, anciennete {2}, temps partiel {3} => prime {4}")
    @CsvSource({
            "'T12345',1,0,1.0,1000.0",
            "'T12345',1,0,0.5,500.0",
            "'T12345',1,2,1.0,1200.0",
            ",1,0,1.0,1000.0",
            "'T12345',,0,1.0,1000.0",
            "'M12345',1,0,1.0,1700.0",
            "'M12345',1,3,1.0,2000.0",
            "'T12345',2,0,1.0,2300.0",
            "'T12345',2,1,1.0,2400.0",
    })
    void testGetPrimeAnnuelle(String matricule, Integer performance, Long nbAnneesAnciennete, Double tempsPartiel, Double primeAttendue) {
        // Given
        Employe employe = EmployeMaker.employeTechnicienPleinTemps().
                but().withMatricule(matricule).
                but().withPerformance(performance).
                but().withDateEmbauche(LocalDate.now().minusYears(nbAnneesAnciennete)).
                but().withTempsPartiel(tempsPartiel).
                build();
        // When
        Double prime = employe.getPrimeAnnuelle();
        // Then
        // Prime de base + prime de perf + prime d'ancienneté au pro rata de son activité
        Assertions.assertEquals(prime, primeAttendue);
    }
}
