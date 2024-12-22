package org.webEda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


class ListaWebsTest {
	ListaWebs listaWebs;
	Palabras palabras=Palabras.getPalabras();
	Web w1,w2,w3,w4,w5,w6,w7;

	@BeforeEach
	void setUp() throws Exception 
	{
		listaWebs=new ListaWebs();
		
		w1=new Web("3d-lifeline.nl");
		w2=new Web("2006timeline.info");
		w3=new Web("2life-style.com");
		w4=new Web("3d-info.fr");
		w5=new Web("transportes.gov.br");
		w6=new Web("1000moneymakingideas.com");
		w7=new Web("007waystomakemoney.com");
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		listaWebs.getLista().clear(); 
		listaWebs.getUrlAId().clear();
		

	}

	@Test
	void testAnadir() 
	{
		System.out.println(" \n................");
		System.out.println("test Añadir");
		listaWebs.anadir(0,w1);
		listaWebs.anadir(1,w2);
		//caso 1
		System.out.println("Añadir webs");
		assertEquals(listaWebs.numeroWebs(),2);
		assertEquals(listaWebs.getLista().get(0),w1);
		assertTrue(listaWebs.getLista().containsValue(w1));  // Comprobar que contiene la web
	    assertTrue(listaWebs.getLista().containsValue(w2)); 
	    //caso 2
	    System.out.println("\nAñadir una web con url existente");
	    listaWebs.anadir(2, w1);
	    assertEquals(listaWebs.numeroWebs(),2);
	    //caso 3
	    System.out.println("\nAñadir una web con id existente");
	    listaWebs.anadir(0, w5);
	    assertEquals(listaWebs.numeroWebs(),2);
	}
	@Test
	void testAnadirEnlaces()
	{
		System.out.println(" \n................");
		System.out.println("test AñadirEnlaces");
		listaWebs.anadir(0,w1);
		listaWebs.anadir(1,w2);
		listaWebs.anadir(2,w3);
		listaWebs.anadir(3,w4);
		//caso 1 añadir enlace web 2 a web1
		listaWebs.anadirEnlace("3d-lifeline.nl", "2006timeline.info");
		listaWebs.imprimirWebsEnlaces();
		
		//caso 2 añadir enlace existente
		System.out.println("\nAñadiendo enlace existente:");
		listaWebs.anadirEnlace("3d-lifeline.nl", "2006timeline.info");
		listaWebs.imprimirWebsEnlaces();
	}
	

	@Test
	void testSalientes()
	{
		System.out.println(" \n................");
		System.out.println("test salientes");
		 
		listaWebs.anadir(0,w1);
	    listaWebs.anadir(1,w2);
	    
	    //No tiene asociado ningún enlace
	    assertEquals(w1.getSalientes().size(),0);
	    System.out.println("lista vacía: "+w1.getSalientes());
	    
	    //Tiene un enlace salientes
	    w2.addEnlace("3d-info.fr");
	    assertEquals(w2.getSalientes().size(),1);
	    System.out.println("lista un enlace saliente: "+w2.getSalientes());
	    
	    //Tiene varios enlaces salientes
	    w1.addEnlace("3d-info.fr");
	    w1.addEnlace("transportes.gov.br");
	    ArrayList<String> es = listaWebs.salientes("3d-lifeline.nl");
	    assertEquals(2, es.size());
	    assertTrue(es.contains("3d-info.fr"));
	    assertTrue(es.contains("transportes.gov.br"));
	    System.out.println("lista con varios enlaces salientes: "+es);
		
	}
	
	@Test
	void testWebsOrdenadas()
	{
		System.out.println(" \n................");
		System.out.println("\ntest websOrdenadas");
		//lista vacía
		ArrayList<String> listaOrdenada=listaWebs.websOrdenadas();
		System.out.println("lista vacia: "+listaOrdenada);
		
		//un elemento
		listaWebs.anadir(0,w1);
		listaOrdenada=listaWebs.websOrdenadas();
		System.out.println("lista con un solo elemento: "+listaOrdenada);

		//varios elementos
		listaWebs.anadir(2,w2);
		listaWebs.anadir(3,w3);
		listaWebs.anadir(4,w4);
		listaWebs.anadir(5,w5);
		listaWebs.anadir(6,w6);
		listaWebs.anadir(7,w7);
		listaOrdenada=listaWebs.websOrdenadas();
		System.out.println("lista de varios elementos: "+listaOrdenada);
		
		listaWebs.getLista().clear();
	}
	
	@Test
	void testWord2Webs()
	{
		System.out.println(" \n................");
		System.out.println("test word2Webs");
		
		palabras.anadirPalabrasADiccionario("money");
		palabras.anadirPalabrasADiccionario("making");
		palabras.anadirPalabrasADiccionario("ideas");
		palabras.anadirPalabrasADiccionario("time");
		palabras.anadirPalabrasADiccionario("life");
		palabras.anadirPalabrasADiccionario("aargh");
		
		listaWebs.anadir(1,w1);
	    listaWebs.anadir(2,w2);
	    listaWebs.anadir(3,w3);
	    listaWebs.anadir(4,w4);
	    listaWebs.anadir(5,w5);
	    listaWebs.imprimir();
	    System.out.println("\n");
		try {
			palabras.asociarPalabraConWebs2(listaWebs);
		} catch (IOException e1) {}
		System.out.println("\n");
		
		
		//palabra clave no existente
		ArrayList<String> webs=listaWebs.word2Webs("buscaminas");
		System.out.println("palabra clave no existe en el diccionario: "+webs);
		assertEquals(webs.size(),0);
		
		//palabra clave no asociada a ninguna pagina
		webs=listaWebs.word2Webs("aargh");
		System.out.println("la palabra no tiene asociado a ninguna web: "+webs);
		assertEquals(webs.size(),0);
		
		//palabra clave asociada a una única web
		webs=listaWebs.word2Webs("time");
		System.out.println("la palabra asociado a una web: "+webs);
		assertEquals(webs.size(),1);
		
		//palabra clave asociada a varias webs
		webs=listaWebs.word2Webs("life");
		System.out.println("la palabra asociado a varias webs: "+webs);
		assertEquals(webs.size(),2);
		
		
		
	}
	
	@Test
	void testWeb2Words()
	{
		
		palabras.anadirPalabrasADiccionario("money");
		palabras.anadirPalabrasADiccionario("making");
		palabras.anadirPalabrasADiccionario("ideas");
		palabras.anadirPalabrasADiccionario("time");
		palabras.anadirPalabrasADiccionario("lifeline");
		palabras.anadirPalabrasADiccionario("aargh");
		
		
		System.out.println(" \n................");
		System.out.println("test web2Words");
		
		listaWebs.anadir(1,w1);
		listaWebs.anadir(4,w4);
		listaWebs.anadir(6,w6);
		try {
			palabras.asociarPalabraConWebs2(listaWebs);
		} catch (IOException e) {}
		
		//no tiene palabras clave asociadas
		ArrayList<String> pals=listaWebs.web2Words("3d-info.fr");
		System.out.println("la lista estaría vacía: "+pals);
		assertEquals(pals.size(),0);
		
		//una palabra clave asociada
		pals=listaWebs.web2Words("3d-lifeline.nl");
		System.out.println("una palabra clave asociada: "+pals);
		assertEquals(pals.size(),1);
		
		//varias palabras asociadas
		pals=listaWebs.web2Words("1000moneymakingideas.com");
		System.out.println("varias palabras asociadas: "+pals);
		assertEquals(pals.size(),3);
	}
	
	@Test 
	void testBorrarWeb()
	{
		System.out.println(" \n................");
		System.out.println("test BorrarWeb");
		listaWebs.anadir(1,w1);
	    listaWebs.anadir(2,w2);
	    listaWebs.anadir(3,w3);
	    listaWebs.anadir(4,w4);
	    listaWebs.anadir(5,w5);
	    assertEquals(listaWebs.numeroWebs(),5);
	    
	    listaWebs.borrarWeb(1);
	    assertEquals(listaWebs.numeroWebs(),4);
	    
	}
	
	
	
	
	

}
