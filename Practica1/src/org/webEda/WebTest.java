package org.webEda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebTest {
	Web web1, web2;
	

	@BeforeEach
	void setUp() throws Exception 
	{
		web1=new Web("60cartimes.com");
		web2= new Web("gatosgraciosos.es");
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
	}

	@Test
	void testGetUrl()
	{
		System.out.println(" \n................");
		System.out.println("Test getUrl");
		assertEquals("60cartimes.com", web1.getUrl());
		System.out.println("Url esperada: 60cartimes.com");
		System.out.println("Resultado: " +web1.getUrl());
	}
	
	@Test
	void testGetSalienteYAddEnlace()
	{
		System.out.println(" \n................");
		System.out.println("Test GetSalientes Y AddEnlace");
		web1.addEnlace("021google.cn");
		web1.addEnlace("1-bingo-download.com"); 
		System.out.println("Añadir dos enlaces, tamaño 2:");
		assertEquals(web1.getSalientes().size(),2);
		web1.imprimirEnlaces();
		
		System.out.println("Añadir un enlace ya existente:");
		web1.addEnlace("021google.cn"); //anadir un enlace ya existente
		web1.imprimirEnlaces();
	}
	
	@Test
	void testGetClavesYAddPalClave()
	{
		System.out.println(" \n................");
		System.out.println("Test GetClaves Y AddPalClave");
		assertEquals(web1.getClaves().size(),0);
		System.out.println("Tamaño 0, sin palabras clave");
		
		web1.addPalClave("cart");
		web1.addPalClave("times");
		
		System.out.println("Añadir una palabra ya existente, resultado:");
		web1.addPalClave("cart"); 
		assertEquals(web1.getClaves().size(),2);
		System.out.println("\nTamaño 2, después de añadir las palabras clave");
		web1.imprimirPalClave();
	}
	
	//@Test
	/*void testToString()
	{
		System.out.println(" \n................");
		System.out.println("Test toString");	
		System.out.println(web1);
		assertEquals("60cartimes.com",web1.toString());
		System.out.println(web2);
		assertEquals("gatosgraciosos.es",web1.toString());
	*/
}
	


