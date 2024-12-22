package org.Practica3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.webEda.ListaWebs;

class RankTest {
	Graph grafo;

	@BeforeEach
	void setUp() throws Exception 
	{
	grafo=new Graph(); //Inicializar un grafo antes de cada test
	}

	@AfterEach
	void tearDown() throws Exception 
	{
	}

	@Test
	void testCalcularRandomWalkRank() {
		fail("Not yet implemented");
	}

	@Test
	void testCalcularPageRank() 
	{
		grafo.enlaces= new HashMap<>();
        grafo.enlaces.put("A", new ArrayList<>());
        grafo.enlaces.put("B", new ArrayList<>(Arrays.asList("C", "A")));
        grafo.enlaces.put("C", new ArrayList<>(Arrays.asList("A")));
        grafo.enlaces.put("D", new ArrayList<>(Arrays.asList("A", "B", "C")));
        
        grafo.print();
        HashMap<String, Double> resultados = grafo.calcularPageRank();

        // Imprimir resultados
        for (String nodo : resultados.keySet()) 
        {
            System.out.println("Nodo: " + nodo + ", PageRank: " + resultados.get(nodo));
        }
	}

}
