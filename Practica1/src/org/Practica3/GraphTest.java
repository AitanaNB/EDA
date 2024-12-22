package org.Practica3;

import org.webEda.ListaWebs;
import org.webEda.Web;
import org.webEda.WebManager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {
	
	Graph grafo;
	long inicio,fin,tiempoTotal;
	String web10,salientes10, web,salientes;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		grafo=new Graph(); //Inicializar un grafo antes de cada test
		web10 ="C:\\Users\\INFORMATICA\\Downloads\\indices.txt";
		salientes10 ="C:\\Users\\INFORMATICA\\Downloads\\enlaces.txt";
		salientes="C:\\Users\\INFORMATICA\\Downloads\\pld-arcs-1-N-2024-25";
		web="C:\\Users\\INFORMATICA\\Downloads\\index-2024-25";
		
		
	}
	
    @Test
    public void testGraph() throws IOException {
        ListaWebs listaWebs = new ListaWebs();
        listaWebs.anadir(0, new Web("0-apr-creditcards.com"));
        listaWebs.anadir(1, new Web("0-60cartimes.com"));
        listaWebs.anadir(2, new Web("0-brune-nue.com"));
        listaWebs.anadir(3, new Web("0-100editions.net"));
        listaWebs.anadir(4, new Web("loquesea.com"));

        listaWebs.anadirEnlace("0-apr-creditcards.com", "0-60cartimes.com");
        listaWebs.anadirEnlace("0-60cartimes.com", "0-brune-nue.com");
        listaWebs.anadirEnlace("0-brune-nue.com", "0-100editions.net");
        
        inicio =System.currentTimeMillis(); //System.nanoTime() para mayor precisión en nanosegundos
        
        grafo.crearGrafo(listaWebs);
        
        fin= System.currentTimeMillis();
        tiempoTotal = fin - inicio;
        
        //System.out.println("Tiempo de ejecución: " + tiempoTotal + " milisegundos");
        // Imprimir el grafo
        grafo.print();

        // Pruebas de conexiones
        assertTrue(grafo.estanConectados("0-apr-creditcards.com", "0-60cartimes.com"));
        assertTrue(grafo.estanConectados("0-apr-creditcards.com", "0-brune-nue.com"));
        assertFalse(grafo.estanConectados("0-brune-nue.com", "loquesea.com"));

        // Prueba de camino
        inicio =System.nanoTime();
        ArrayList<String> camino = grafo.estanConectados2("0-apr-creditcards.com", "0-100editions.net");
        fin= System.nanoTime();
        tiempoTotal = fin - inicio;
        assertNotNull(camino);
        assertEquals(4, camino.size());
        assertEquals("0-apr-creditcards.com", camino.get(0));
        assertEquals("0-100editions.net", camino.get(3));
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        System.out.println(camino);
        
        //Archivos de 10 enlaces
        System.out.println(" \n................");
        System.out.println("Test leyendo archivos de 10 enlaces");
        WebManager.getWebManager().cargarArchivosGrafo(web10, salientes10);
        listaWebs=WebManager.getWebManager().getLista();
        grafo.crearGrafo(listaWebs);
        
        // Pruebas de conexiones
        assertTrue(grafo.estanConectados("cero.com", "uno.cn"));
        assertTrue(grafo.estanConectados("uno.cn", "9.com"));
        assertFalse(grafo.estanConectados("dos.net", "5.ro"));

        // Prueba de camino
        inicio =System.nanoTime();
        camino = grafo.estanConectados2("cero.com", "diez.es");
        fin= System.nanoTime();
        tiempoTotal = fin - inicio;
        assertNotNull(camino);
        
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos");
        System.out.println(camino);
        if(camino.size()==3)
        {System.out.println("Se ha escogido el camino más corto\n");
        }
        
        inicio =System.nanoTime();
        camino = grafo.estanConectados2("dos.net", "9.com");
        fin= System.nanoTime();
        tiempoTotal = fin - inicio;
        assertNull(camino);
        System.out.println("Tiempo de ejecución: " + tiempoTotal + " nanosegundos \n");
        System.out.println(camino);
        
      //Archivos grandes de 1era práctica
        WebManager.getWebManager().resetear();
        System.out.println(" \n................");
        System.out.println("Test leyendo archivos grandes");
        WebManager.getWebManager().cargarArchivosGrafo(web, salientes);
        listaWebs=WebManager.getWebManager().getLista();
        grafo.crearGrafo(listaWebs);
        
        //Pruebas de conexiones
        	//Datos conectados
        inicio =System.currentTimeMillis();
        assertTrue(grafo.estanConectados("0-00.pl", "billbird.pl"));
        fin=System.currentTimeMillis();
        System.out.println("Tiempo de ejecución conexión: " + (fin-inicio) + " milisegundo(s)");
        	//Datos no conectados
        inicio =System.currentTimeMillis();
        assertFalse(grafo.estanConectados("0-00.pl", "0-100.com.cn"));
        fin=System.currentTimeMillis();
        System.out.println("Tiempo de ejecución conexión: " + (fin-inicio) + " milisegundo(s)");
        
        //Pruebas de camino
    		//Datos conectados: 
        //0->532973->236229->593308->1415112->596608->236229
	    inicio =System.nanoTime();
	    camino=grafo.estanConectados2("0-00.pl", "billbird.pl");
	    fin=System.nanoTime();
	    System.out.println("\nTiempo de ejecución camino: " + (fin-inicio) + " nanosegundo(s)");
	    System.out.println(camino);
	    
	    
	    inicio =System.nanoTime();
	    camino=grafo.estanConectados2("0-00.pl", "energa.pl");
	    fin=System.nanoTime();
	    System.out.println("\n Tiempo de ejecución camino: " + (fin-inicio) + " nanosegundo(s)");
	    System.out.println(camino);
	    
	    	//Datos no conectados
	    inicio =System.currentTimeMillis();
	    camino=grafo.estanConectados2("0-00.pl", "0-100.com.cn");
	    fin=System.currentTimeMillis();
	    System.out.println("\nTiempo de ejecución camino: " + (fin-inicio) + " milisegundo(s)");
	    System.out.println(camino);
	    
	    	//Datos no conectados
	    inicio =System.currentTimeMillis();
	    camino=grafo.estanConectados2("energiadalvento.com", "webservicesrl.com");
	    fin=System.currentTimeMillis();
	    System.out.println("\nTiempo de ejecución camino: " + (fin-inicio) + " milisegundo(s)");
	    System.out.println(camino);

	        
    }
}