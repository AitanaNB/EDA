package org.webEda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

class PalabrasTest {
	ListaWebs listaWebs;
	Palabras palabras;
	Web w1, w2, w3;

	@BeforeEach
	void setUp() throws Exception {
		listaWebs = new ListaWebs();
		palabras = Palabras.getPalabras();
		w1 = new Web("3d-lifeline.nl");
		w2 = new Web("2006timeline.info");
		w3 = new Web("2life-style.com");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		listaWebs.anadir(1, w1);
		listaWebs.anadir(2, w2);
		
		assertEquals(listaWebs.getLista().size(), 2);
	}

	@Test
	void testAsociarPalabraAWebs() throws IOException {
		System.out.println(" \n................");
		System.out.println("Test AsociarPalabraAWebs2");
		listaWebs.anadir(1, w1);
		listaWebs.anadir(2, w2);
		listaWebs.anadir(3, w3);
		
		listaWebs.imprimir();
		palabras.anadirPalabrasADiccionario("line");
		palabras.anadirPalabrasADiccionario("time");
		palabras.anadirPalabrasADiccionario("life");
		this.palabras.imprimirDiccionario();;
		
		palabras.asociarPalabraConWebs2(listaWebs);
		
		/*
		 * w1.addEnlace("3d-info.fr"); w1.addEnlace("transportes.gov.br");
		 * ArrayList<String> es=listaWebs.salientes("3d-lifeline.nl");
		 * assertEquals(es.size(),2);
		 */
		System.out.println("\nContenido de palabraAWebs:");
		palabras.imprimirPalabrasAWebs();
	}
}
