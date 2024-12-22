package org.webEda;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebManagerTest {
	WebManager webManager;
	String web0, web1, web10, web1000, webFull, enlaces;
	HashMap<Integer, Web> listaEnlaces;
	Web url1;

	@BeforeEach
	void setUp() throws Exception 
	{
		webManager=WebManager.getWebManager();
		//Definir las rutas de los archivos
		web0="F:\\Docs\\0webs.txt";
		web1="F:\\Docs\\1web.txt";
		web10="F:\\Docs\\10webs.txt";
		web1000="F:\\Docs\\1000webs.txt";
		webFull="F:\\Docs\\index-2024-25";
		enlaces="F:\\Docs\\enlaces.txt";
		url1= new Web("transportes.gov.br");
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		webManager.resetear();
		//listaWebs.getLista().clear(); 
		

	}

	@Test
	void testNumeroWebs() throws IOException 
	{
		System.out.println(" \n................");
		System.out.println("Test numeroWebs");
			//caso 1: 0 webs
		webManager.resetear();webManager.lectorWebs(web0);
		assertEquals(0, webManager.numeroWebs());
		System.out.println("Archivo 0 web: " + webManager.numeroWebs());
			//caso 2: 1 web
		webManager.resetear(); webManager.lectorWebs(web1);
		assertEquals(1, webManager.numeroWebs());
		System.out.println("Archivo 1 web:" + webManager.numeroWebs());
		
			//caso 3: 10 webs
		webManager.resetear(); webManager.lectorWebs(web10);
		assertEquals(10, webManager.numeroWebs());
		System.out.println("Archivo 10 web:" + webManager.numeroWebs());
		
			//cas0 4: 1000 webs
		webManager.resetear(); webManager.lectorWebs(web1000);
		assertEquals(1000, webManager.numeroWebs());
		System.out.println("Archivo 1000 web:" + webManager.numeroWebs());
		
			//caso 5: archivo grande de webs
		webManager.resetear(); webManager.lectorWebs(webFull);
		assertEquals(2039805, webManager.numeroWebs());
		System.out.println("Archivo 2039805 web: " + webManager.numeroWebs());
		System.out.println("Fin."); //LAS LEE TODASSSSS!!!!!
	}

	@Test
	void testImprimir() throws IOException 
	{
		
		
		System.out.println(" \n................");
		System.out.println("Test Imprimir");
			//caso 1: 0 webs
		webManager.lectorWebs(web0);
		System.out.println("Archivo 0 web: ");
		webManager.imprimir();
		webManager.resetear();
			//caso 2: 1 web
		webManager.lectorWebs(web1);
		System.out.println("\nArchivo 1 web: ");
		webManager.imprimir();
		webManager.resetear();
			//caso 3: 10 webs
		webManager.lectorWebs(web10);
		System.out.println("\nArchivo 10 web: ");
		webManager.imprimir();
		webManager.resetear();
			/*caso 4: 1000 webs funciona
		webManager.lectorWebs(web1000);
		System.out.println("\nArchivo 10000 web: ");
		webManager.imprimir();
		webManager.resetear();*/
		
		
	}


	@Test
	void testLectorSalientes() throws IOException 
	{
		System.out.println(" \n................");
		System.out.println("Test LectorSalientes");
			//caso 1: archivo vacio
		webManager.resetear();
		System.out.println("Archivo vacío:");
		webManager.lectorSalientes(web0);
		webManager.getLista().imprimirWebsEnlaces();
			//archivo con varios enlaces sin inicializar lista de webs
		webManager.resetear();
		System.out.println("\nArchivo con varios enlaces sin inicializar lista de webs");
		webManager.lectorSalientes(enlaces);
		webManager.getLista().imprimirWebsEnlaces();
			//archivo con varios enlaces  inicializando lista  webs
		webManager.resetear();
		System.out.println("\nArchivo con varios enlaces inicializando lista de webs");
		webManager.lectorWebs(web10);
		webManager.lectorSalientes(enlaces);
		webManager.getLista().imprimirWebsEnlaces();
		
	}
	
	@Test
	void testCrearFicheroWebs() throws IOException
	{
		System.out.println(" \n................");
		System.out.println("Test CrearFicheroWebs");
		webManager.lectorWebs(web10);
		webManager.getLista().anadir(10, url1);
		webManager.crearFicheroWebs("nuevoFichero0.txt", webManager.getLista().getLista());
		System.out.println("Mirar archivo en el directorio de trabajo");
	}
	
	@Test
	void testAddWebYCrearFicehro() throws IOException
	{
		System.out.println(" \n................");
		System.out.println("Test AddWebYCrearFicehro");
		
		//caso 1
		System.out.println("Añadir una web a una lista vacía");
		assertEquals(0, webManager.numeroWebs());
		webManager.addWebYCrearFicehro("nuevoFichero1.txt", "soloUnaWeb.au");
		System.out.println("nMirar archivo en el directorio de trabajo");
		//caso 2
		webManager.resetear();
		webManager.lectorWebs(web1);
		System.out.println("Añadir una web que no existe");
		webManager.addWebYCrearFicehro("nuevoFichero2.txt", "nombreAleatorioWeb.es");
		System.out.println("Mirar archivo en el directorio de trabajo");
		//caso 2
		System.out.println("\nSi se intenta añadir una web que ya está:");
		webManager.addWebYCrearFicehro("nuevoFichero3.txt", "nombreAleatorioWeb.es");
		assertEquals(2, webManager.numeroWebs());
	}
	
	@Test
	void testAddEnlaceYCrearFichero() throws IOException
	{
		System.out.println(" \n................");
		System.out.println("Test AddeEnlaceYCrearFichero");
		webManager.lectorWebs(web1);
		//caso 1
		System.out.println("Añadir un enlace que no existe a una web existente");
		webManager.addEnlaceYCrearFichero("nuevoEnlaces0.txt", "0-00.pl", "0-24-sex.de");
		System.out.println("Mirar archivo en el directorio de trabajo");
		assertEquals(2, webManager.numeroWebs());
		//caso 2
		System.out.println("\nSi se intenta añadir una web que ya está:");
		webManager.addEnlaceYCrearFichero("nuevoEnlaces1.txt", "0-00.pl", "0-24-sex.de");
		//caso 3
		System.out.println("\nAñadir un enlace que no existe a una web que tampoco existe");
		webManager.addEnlaceYCrearFichero("nuevoEnlaces2.txt", "webnueva.pl", "otraweb.d");
		assertEquals(4, webManager.numeroWebs());
		
	}
	
	@Test 
	void testArchivosGrandes()throws IOException
	{
		System.out.println(" \n................");
		System.out.println("Test Archivos Grandes");
		
        String rutaWeb="F:\\Docs\\index-2024-25"; //F:\\Docs\\index-2024-25
        String rutaPal="F:\\Docs\\words.txt"; //F:\\Docs\\words.txt
        String rutaEnl="F:\\Docs\\pld-arcs-1-N-2024-25";//F:\\Docs\\pld-arcs-1-N-2024-25
        
        webManager.cargarTodosArchivos(rutaWeb, rutaPal, rutaEnl);
        
        

       
	}

	/*no creo que haga falta 
	 * @Test
	void testCargarTodosArchivos() {
		fail("Not yet implemented");
	}*/

}
