package org.Alumnos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderedDoubleLinkedListTest {

OrderedDoubleLinkedList<String> lista;
	
	@BeforeEach
	void setUp() throws Exception {
		lista=new OrderedDoubleLinkedList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		lista=null;
	}
	
	@Test
	void testAdd() {
		//lista vacia
		lista.add("casa");
		assertEquals(1, lista.size()); // [casa]
		
		//lista con un solo elemento
		//anadir elem al principio
		lista.add("ama");
		assertEquals(2, lista.size());
		assertEquals("ama",lista.first()); // [ama, casa]
		
		//anadir elem al final
		lista.remove("ama");
		lista.add("palo");
		assertEquals(2,lista.size());
		assertEquals("palo",lista.last()); // [casa, palo]
		
		//lista con varios elementos
		//anadir al principio
		lista.add("ala");
		assertEquals(3,lista.size());
		assertEquals("ala",lista.first()); // [ala, casa, palo]
		
		//anadir en el medio
		lista.add("dado");
		assertEquals(4,lista.size()); // [ala, casa, dado, palo]
		
		//anadir al final
		lista.add("pueblo");
		assertEquals(5,lista.size());
		assertEquals("pueblo",lista.last()); // [ala, casa, dado, palo, pueblo]
	}

	@Test
	void testMerge() {
		OrderedDoubleLinkedList<String> lista1 = new OrderedDoubleLinkedList<>();	//lista actual
		OrderedDoubleLinkedList<String> lista2 = new OrderedDoubleLinkedList<>();	//lista de entrada

		//1. ambas listas vacias
		lista1.merge(lista2);
		assertEquals(0, lista1.size());
		lista1.visualizarNodos();
		
		//2. lista actual vacia y lista de entrada NO vacia
		lista2.add("ama");				// anadimos elems a la lista a fusionar
		lista2.add("nada");
		lista2.add("perro");
		lista1.merge(lista2);			// fusionamos lista vacia (lista1) con lista2
		lista1.visualizarNodos();		// vemos la lista resultante
		assertEquals(3, lista1.size());	// se espera que el tamaño de la lista sea 3  [ama, nada, perro]
		
		//3. lista actual NO vacia y lista de entrada vacia
		//3.1 lista actual tiene 1 elem
		lista2.remove("ama");			// limpiar lista2 para asegurarse de que esté vacía
		lista2.remove("nada");
		lista2.remove("perro");
		lista1.remove("ama");			// limpiar lista1 para asegurarse de que esté vacía
		lista1.remove("nada");
		lista1.remove("perro");
		lista1.add("pepe");
		lista1.merge(lista2);			// fusionamos lista1 con lista vacia (lista2)
		lista1.visualizarNodos();
		assertEquals(1,lista1.size());	// tamano tiene que ser 1 // [pepe]
		
		//3.2 lista de actual tiene mas de 1 elem
		lista1.add("ama");				// anadir elems a la lista de entrada
		lista1.add("nada");
		lista1.add("perro");			
		lista1.merge(lista2);			// fusionamos lista1 con lista vacia (lista2)
		lista1.visualizarNodos();	
		assertEquals(4,lista1.size());	// [ama, nada, pepe, perro]

		// 4. Ambas listas con un solo elemento
		lista1.remove("pepe");			// limpiar lista1 para asegurarse de que esté vacía
		lista1.remove("nada");
		lista1.remove("perro");			// "ama" se queda sin borrar
		lista2.add("casa");
		lista1.merge(lista2);
		lista1.visualizarNodos();
		assertEquals(2,lista1.size());	// [ama, casa]
				
		// 5. Fusión de dos listas ordenadas sin elementos duplicados
		lista1.remove("casa");
		lista1.add("pedro");
		lista1.add("mesa"); 			//tenemos en cuenta que lista1 ya tiene "ama" y lista2 ya tiene "casa"
		lista1.merge(lista2);
		lista1.visualizarNodos();
		assertEquals(4,lista1.size());	// [ama, casa, mesa, pedro]
		
		// 6. Fusión de dos listas ordenadas con duplicados
		lista1.add("ama");				//lista1 tiene "ama" duplicado	 [ama, ama, casa, mesa, pedro]
		lista2.add("ama");				//lista2 tiene "ama" tambien	 [ama, casa]
		lista1.merge(lista2);
		lista1.visualizarNodos();
		assertEquals(7,lista1.size());	// [ama, ama, ama, casa, casa, mesa, pedro]
		
		// 7. Fusión donde la lista es un subconjunto de la otra
		lista1.remove("pedro");
		lista1.remove("casa");
		lista1.removeAll("ama");		// lista1: [casa, mesa]
		lista2.add("pedro");
		lista2.add("mesa");			// lista2: [ama, casa, mesa, pedro]
		lista1.merge(lista2);
		lista1.visualizarNodos();
		assertEquals(6,lista1.size());	// [ama, casa, casa, mesa, mesa, pedro]
		
		 // 8. Fusión de una lista con todos los elementos mayores que la lista actual
		lista1.removeAll("mesa");
		lista1.removeAll("ama"); 		// limpiar lista1 --> [ ]
		lista1.removeAll("casa"); 
		lista1.remove("pedro");
		
	    lista2.remove("ama");		
	    lista2.remove("mesa");			
	    lista2.remove("casa");
	    lista2.remove("pedro");	   // limpiar lista2 --> [ ]
	    
	    lista1.add("uno");
	    lista1.add("dos");			//lista1 [dos, uno]
	    lista2.add("tres");
	    lista2.add("cuatro");		
	    lista2.add("cinco");		//lista2 [cinco, cuatro, tres]
	    lista1.merge(lista2); 
	    assertEquals(5, lista1.size()); 	// [cinco, cuatro, dos, tres, uno]
	    lista1.visualizarNodos();

	    // 9. Fusión de una lista con todos los elementos menores que la lista actual
	    lista1.remove("uno");
	    lista1.remove("dos");
	    lista1.remove("tres");
	    lista1.remove("cuatro");
	    lista1.remove("cinco");
	    lista1.add("seis");
	    lista1.add("siete");		// lista1 [seis, siete]
	    							// lista2 sigue igual
	    lista1.merge(lista2); 
	    assertEquals(5, lista1.size()); 	// [cinco, cuatro, seis, siete, tres]
	    lista1.visualizarNodos(); 
	}	

}
