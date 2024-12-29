package org.Practica3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.webEda.ListaWebs;
import org.webEda.Palabras;
import org.webEda.Web;
import org.webEda.WebManager;

class RankTest {
	Graph grafo;
	String web10, salientes10, web, salientes,pal;
	long inicio, fin, tiempoTotal;
	HashMap<String, Double> rdo;
	HashMap<String, Double> pageRank;
	ListaWebs listaWebs;

	@BeforeEach
	void setUp() throws Exception {
		grafo = new Graph(); // Inicializar un grafo antes de cada test
		listaWebs = new ListaWebs();
		web10 = "C:\\Users\\AITANA\\Downloads\\indices.txt";
		salientes10 = "C:\\Users\\AITANA\\Downloads\\enlaces.txt";
		// archivos grandes:
		salientes = "C:\\Users\\AITANA\\Downloads\\pld-arcs-1-N-2024-25";
		web = "C:\\Users\\AITANA\\Downloads\\index-2024-25";
		pal="C:\\Users\\AITANA\\Downloads\\words.txt";
		
		Palabras.getPalabras().resetear();
		listaWebs=new ListaWebs();
		WebManager.getWebManager().resetear();

	}

	@Test
	public void testUnElemento() {
		System.out.println();
		System.out.println(" \n................");
		System.out.println("Test un elemento");
		listaWebs.anadir(0, new Web("web1"));
		grafo.crearGrafo(listaWebs);
		grafo.print();
		rdo = grafo.calcularRandomWalkRank(1000);
		assertEquals(rdo.size(), 1);
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}

		pageRank = grafo.calcularPageRank();
		assertEquals(pageRank.size(), 1);
		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}
	}

	@Test
	public void testDosElementosConectados() {
		System.out.println();
		System.out.println(" \n................");
		System.out.println("Test dos elementos conectados");
		listaWebs.anadir(0, new Web("web1"));
		listaWebs.anadir(1, new Web("web2"));
		listaWebs.anadirEnlace("web1", "web2");
		grafo.crearGrafo(listaWebs);
		grafo.print();
		rdo = grafo.calcularRandomWalkRank(1);
		assertEquals(rdo.size(), 2);
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}

		pageRank = grafo.calcularPageRank();
		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}

	}

	@Test
	public void testDosElementosCiclo() {
		System.out.println();
		System.out.println(" \n................");
		System.out.println("Test dos elementos ciclo");
		listaWebs.anadir(0, new Web("web1"));
		listaWebs.anadir(1, new Web("web2"));
		listaWebs.anadirEnlace("web1", "web2");
		listaWebs.anadirEnlace("web2", "web1");
		grafo.crearGrafo(listaWebs);
		grafo.print();
		rdo = grafo.calcularRandomWalkRank(1);
		assertEquals(rdo.size(), 2);
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}

		pageRank = grafo.calcularPageRank();
		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}

	}

	@Test
	public void testDosElementosNoConectados() {
		System.out.println();
		System.out.println(" \n................");
		System.out.println("Test dos elementos no conectados");
		listaWebs.anadir(0, new Web("web1"));
		listaWebs.anadir(1, new Web("web2"));
		grafo.crearGrafo(listaWebs);
		grafo.print();
		rdo = grafo.calcularRandomWalkRank(1);
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}

		pageRank = grafo.calcularPageRank();
		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}
	}

	@Test
	public void testVariosElementos() {
		System.out.println();
		System.out.println(" \n................");
		System.out.println("Test varios elementos");
		listaWebs.anadir(0, new Web("A"));
		listaWebs.anadir(1, new Web("B"));
		listaWebs.anadir(2, new Web("C"));
		listaWebs.anadir(3, new Web("D"));

		/*
		 * listaWebs.anadirEnlace("A", "B"); listaWebs.anadirEnlace("A", "C");
		 */

		listaWebs.anadirEnlace("B", "A");
		listaWebs.anadirEnlace("B", "C");

		listaWebs.anadirEnlace("C", "A");

		listaWebs.anadirEnlace("D", "A");
		listaWebs.anadirEnlace("D", "B");
		listaWebs.anadirEnlace("D", "C");
		grafo.crearGrafo(listaWebs);
		grafo.print();

		// calcular el randomWalkRank
		rdo = grafo.calcularRandomWalkRank(1000);
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}

		// calcular el pageRank
		pageRank = grafo.calcularPageRank();

		// crear un array del tamano de los valores en el mapa
		double[] pr = new double[pageRank.size()];

		// indice para llenar el array
		int i = 0;
		for (Double value : pageRank.values()) {
			pr[i++] = value; // agregar cada valor al array
		}
		String[] id2String = pageRank.keySet().toArray(new String[0]);

		// imprimir los 3 mejores nodos segun el PageRank
		System.out.println("Top 3 nodos por PageRank:");
		grafo.imprimirLosDeMejorPageRank(pr, 3, id2String);

		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}
	}

	@Test
	public void test10Enlaces() throws IOException {
		System.out.println();
		// Archivos de 10 enlaces
		System.out.println(" \n................");
		System.out.println("Test leyendo archivos de 10 enlaces");
		WebManager.getWebManager().cargarArchivosGrafo(web10, salientes10);
		listaWebs = WebManager.getWebManager().getLista();
		grafo.crearGrafo(listaWebs);

		grafo.print();

		inicio = System.nanoTime();
		// calcular el randomWalkRank
		rdo = grafo.calcularRandomWalkRank(1000);
		fin = System.nanoTime();
		tiempoTotal = fin - inicio;
		
		// Imprimir resultados
		System.out.println("\nRandom Walk Rank:");
		for (String nodo : rdo.keySet()) {
			System.out.println("Nodo: " + nodo + ", WalkRank: " + rdo.get(nodo));
		}
		System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");

		// calcular el pageRank
		pageRank = grafo.calcularPageRank();

		// crear un array del tamano de los valores en el mapa
		double[] pr = new double[pageRank.size()];

		// indice para llenar el array
		int i = 0;
		for (Double value : pageRank.values()) {
			pr[i++] = value; // agregar cada valor al array
		}
		String[] id2String = pageRank.keySet().toArray(new String[0]);

		// imprimir los 3 mejores nodos segun el PageRank
		System.out.println("Top 3 nodos por PageRank:");
		grafo.imprimirLosDeMejorPageRank(pr, 3, id2String);
		
		// Imprimir resultados
		System.out.println("\nPage Rank:");
		for (String nodo : pageRank.keySet()) {
			System.out.println("Nodo: " + nodo + ", PageRank: " + pageRank.get(nodo));
		}
	}
	
	/*@Test
	public void testArchivoOriginal() throws IOException {
		WebManager.getWebManager().resetear();
		//Archivos original
        System.out.println(" \n................");
        System.out.println("Test leyendo archivo original");
        WebManager.getWebManager().cargarArchivosGrafo(web, salientes);
        listaWebs=WebManager.getWebManager().getLista();
        grafo.crearGrafo(listaWebs);
        inicio =System.nanoTime();
        // calcular el randomWalkRank
        rdo = grafo.calcularRandomWalkRank(1000); 
        fin= System.nanoTime();
        tiempoTotal = fin - inicio;
	    //System.out.println("Random Walk Rank: " + rdo);
	    System.out.println("Tiempo de ejecución Random Walk: " + tiempoTotal + " nanosegundos \n");
	    
	    // calcular el pageRank
	    inicio =System.nanoTime();
	    pageRank=grafo.calcularPageRank();
	    //System.out.println("Page Rank: " + pageRank);
	    fin= System.nanoTime();
        tiempoTotal = fin - inicio;
        System.out.println("Tiempo de ejecución Page Rank: " + tiempoTotal + " nanosegundos \n");
	    // crear un array del tamano de los valores en el mapa
	    double[] pr = new double[pageRank.size()];

	    // indice para llenar el array
	    int i = 0;
	    for (Double value : pageRank.values()) {
	        pr[i++] = value; // agregar cada valor al array
	    }
	    String[] id2String = pageRank.keySet().toArray(new String[0]);

	    // imprimir los 3 mejores nodos segun el PageRank
	    System.out.println("Top 3 nodos por PageRank:");
	    inicio =System.nanoTime();
	    grafo.imprimirLosDeMejorPageRank(pr, 3, id2String);
	    fin= System.nanoTime();
        tiempoTotal = fin - inicio;
        System.out.println("Tiempo de ejecución impresión: " + tiempoTotal + " nanosegundos \n");
	}*/
	
	//************* TEST BUSCAR PÁGINAS *************
	
	@Test
    void testGrafoVacio() {
		System.out.println(" \n................");
    	System.out.println("testGrafoVacio");
		ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        // Imprimir resultados
 		System.out.println("\nPáginas según claves y PageRank:");
 		for (Par par :resultado) {
 			System.out.println(par);
 		}
    }

    @Test
    void testUnaPaginaSinEnlaces() throws IOException {
    	System.out.println(" \n................");
    	System.out.println("testUnaPaginaSinEnlace");
        Web web = new Web("gemoneybank.ch");
        listaWebs.anadir(0, web);
        
        Palabras.getPalabras().anadirPalabrasADiccionario("money");
        Palabras.getPalabras().anadirPalabrasADiccionario("bank");
        
        grafo.crearGrafo(listaWebs);
        Palabras.getPalabras().asociarPalabraConWebs2(listaWebs);
        System.out.println(Palabras.getPalabras().getPalabrasAWebs());
        
        inicio=System.nanoTime();
        ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        fin=System.nanoTime();
        tiempoTotal=fin-inicio;
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        
        // Imprimir resultados
 		System.out.println("\nPáginas según claves y PageRank:");
 		for (Par par :resultado) {
 			System.out.println(par);
 		}

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("gemoneybank.ch", resultado.get(0).getUrl());
    }
 

    @Test
    void testDosPaginasConAmbasPalabrasClave() throws IOException {
        // Dos paginas con ambas palabras clave y enlaces
    	System.out.println(" \n................");
    	System.out.println("testDosPaginasConAmbasPalabrasClave");

        listaWebs.anadir(0, new Web("www.banksmoney.com"));
        listaWebs.anadir(1, new Web("www.banksgivemoneyfornothing.com"));

        grafo.crearGrafo(listaWebs);
        Palabras.getPalabras().anadirPalabrasADiccionario("money");
        Palabras.getPalabras().anadirPalabrasADiccionario("bank");
        Palabras.getPalabras().asociarPalabraConWebs2(listaWebs);
        inicio=System.nanoTime();
        ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        fin=System.nanoTime();
        tiempoTotal=fin-inicio;
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");

        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        // Imprimir resultados
  		System.out.println("\nPáginas según claves y PageRank:");
  		for (Par par :resultado) {
  			System.out.println(par);
  		}
    }

    @Test
    void testSinInterseccionDePalabrasClave() throws IOException {
        // Paginas con palabras clave diferentes
    	System.out.println(" \n................");
    	System.out.println("testSinInterseccionDePalabrasClave");

        listaWebs.anadir(0, new Web("www.bank.com"));
        listaWebs.anadir(1, new Web("www.money.com"));
        
        Palabras.getPalabras().anadirPalabrasADiccionario("money");
        Palabras.getPalabras().anadirPalabrasADiccionario("bank");
        Palabras.getPalabras().asociarPalabraConWebs2(listaWebs);

        grafo.crearGrafo(listaWebs);
        inicio=System.nanoTime();
        ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        fin=System.nanoTime();
        tiempoTotal=fin-inicio;
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(0, resultado.size(), "No debería haber páginas coincidentes.");
        // Imprimir resultados
 		System.out.println("\nPáginas según claves y PageRank:");
 		for (Par par :resultado) {
 			System.out.println(par);
 		}
    }

    @Test
    void testVariasPaginasConAmbasPalabrasClave() throws IOException {
        // Varias paginas con ambas palabras clave y diferentes valores de PageRank
    	System.out.println(" \n................");
    	System.out.println("testVariasPaginasConAmbasPalabrasClave");
    	
        listaWebs.anadir(0, new Web("www.banksmoney.com"));
        listaWebs.anadir(1, new Web("www.mymoneyinthebank.com"));
        listaWebs.anadir(2, new Web("www.banksgivemoneyfornothing.com"));
        listaWebs.anadirEnlace("www.banksmoney.com", "www.banksgivemoneyfornothing.com");
        listaWebs.anadirEnlace("www.mymoneyinthebank.com", "www.banksgivemoneyfornothing.com");

        Palabras.getPalabras().anadirPalabrasADiccionario("money");
        Palabras.getPalabras().anadirPalabrasADiccionario("bank");
        Palabras.getPalabras().asociarPalabraConWebs2(listaWebs);
        grafo.crearGrafo(listaWebs);
        inicio=System.nanoTime();
        ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        fin= System.nanoTime();
        tiempoTotal=fin-inicio;
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        // Verificar resultado
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
       
        // Imprimir resultados
 		System.out.println("\nPáginas según claves y PageRank:");
 		for (Par par :resultado) {
 			System.out.println(par);
 		}
    }
    
    @Test
    void testArchivoGrande() throws IOException {
    	System.out.println(" \n................");
    	System.out.println("testArchivoGrande");

    	WebManager.getWebManager().cargarTodosArchivos(web, pal, salientes);
        listaWebs=WebManager.getWebManager().getLista();
        System.out.println(listaWebs.numeroWebs());
        grafo.crearGrafo(listaWebs);
        
        inicio=System.nanoTime();
        ArrayList<Par> resultado = grafo.buscarPaginas("money", "bank");
        fin=System.nanoTime();
        assertNotNull(resultado);
        //System.out.println(resultado);
        tiempoTotal=fin-inicio;
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        // Imprimir resultados
 		System.out.println("\nPáginas según claves y PageRank:");
 		for (Par par :resultado) {
 			System.out.println(par);
 		}
    }
}


