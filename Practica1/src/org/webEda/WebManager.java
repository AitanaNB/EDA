package org.webEda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//clase encargada de manejar los ficheros de las webs
public class WebManager

{
	private static WebManager miWebManager = new WebManager();
	private ListaWebs lista = new ListaWebs(); // hashmap con ids y nombres de webs

	// si el diccionario no tiene funcionalidades, meterlo aqui?

	public static WebManager getWebManager() {
		return miWebManager;
	}

	public int numeroWebs() {
		return this.lista.numeroWebs();
	}

	public void imprimir() {
		this.lista.imprimir();
	}

	public ListaWebs getLista() {
		return getWebManager().lista;
	}

	public void resetear() // para los casos de prueba
	{
		this.lista.getLista().clear();
		this.lista.getUrlAId().clear();
	}

	// Leer los ficheros y cargar los datos
	
	public void lectorWebs(String pNombre) throws IOException 
	
	{
		try (BufferedReader lector = new BufferedReader(new FileReader(pNombre))) {
			// crear un BF a traves de fileReader, que lee un fichero nombre x

			// Leer el archivo index y cargar webs
			String actual;

			while ((actual = lector.readLine()) != null) { // formato numero ::: web

				// ignorar las lineas vacias, xa cuando se eliminen webs
				if (actual.trim().isEmpty()) {
					continue;
				}
				String[] partes = actual.split("\\s+:+\\s+"); // divide el string

				try// por si el numero da error
				{
					int id = Integer.parseInt(partes[0]); // lo que contiene el id
					String url = partes[1]; // contiene nombre de la web
					this.lista.anadir(id, new Web(url));
				} catch (NumberFormatException e) {
					System.out.println("Error al convertir el ID a número: " + partes[0]);
				}

			}
			// lector.close(); en principio no hace falta x try catch
		} catch (IOException e) // excepciones de entrada/salida
		{
			e.printStackTrace();
		}

	}
	// pre: el formato de archivo es correcto
	// post: lee el archivo de enlaces salientes y los añade a las webs
	// correspondientes

	public void lectorSalientes(String pNombre) throws IOException {
		// Leer el archivo de enlaces y los enlaces// salientes, y asignarlos a cada web
		try (BufferedReader lector = new BufferedReader(new FileReader(pNombre))) {
			// Leer el archivo index y cargar webs
			String actual;

			while ((actual = lector.readLine()) != null) { // formato numero >>> relacion ##relacion##...
				if (actual.trim().isEmpty()) {
					continue;
				}

				String[] partes = actual.split("\\s+>+\\s+"); // parte del numero de web
				int idWebPrinc = Integer.parseInt(partes[0].trim());

				// buscar la web en lista
				Web webPrincipal = lista.getLista().get(idWebPrinc);

				if (webPrincipal != null) {
					// Asignar los enlaces salientes a la web principal
					ArrayList<String> enlSalientes = new ArrayList<>();

					if (partes.length > 1) { // verificar que hay algo después de >>
						String partes2 = partes[1].trim();

						// Dividir los números después de `>>>`
						String[] enlaces = partes2.split("\\D+");

						// añadir nums->NOMBRE al array de enlaces salientes si no estan vacios
						for (String enlace : enlaces) {
							if (!enlace.isEmpty()) {
								int idEnlace = Integer.parseInt(enlace.trim());// pasar a numero
								Web webEnlace = lista.getLista().get(idEnlace);// obtener la web con ese id

								if (webEnlace != null) {
									enlSalientes.add(webEnlace.getUrl()); // Agregar la URL de la web al ArrayList
								} else {
									System.out.println("No se encontró el enlace: " + idEnlace);
								}
							}
						}

						// asignar array a la web principal
						for (String enlace : enlSalientes) {
							webPrincipal.addEnlace(enlace);
						}
					}
				} else {
					System.out.println("No se encontró la web principal con ID:" + idWebPrinc);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void crearFicheroWebs(String pNombre, HashMap<Integer, Web> datos) {
		// usaría esto: HashMap<Integer, Web> datos = this.lista.getLista();
		try {
			PrintWriter writer = new PrintWriter(pNombre, "UTF-8");

			for (Integer key : datos.keySet()) {
				Web web = datos.get(key);
				writer.println(key + "   :::   " + web.toString());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addWebYCrearFicehro(String pNombre, String pWeb) {
		int id = this.lista.numeroWebs(); // no hace falta sumar porque empieza desde el 0
		this.lista.anadir(id, new Web(pWeb));
		crearFicheroWebs(pNombre, this.lista.getLista());
	}

	public void crearFicheroEnlaces(String pNombre, HashMap<Integer, Web> datos) {
		try {
			PrintWriter writer = new PrintWriter(pNombre, "UTF-8");

			for (Integer id : datos.keySet()) { // iterar sobre todas las webs

				writer.print(id + " >>>> ");
				ArrayList<Integer> ids = new ArrayList<>();// crear la lista vacia con los ids

				for (String enlace : datos.get(id).getSalientes()) // pasar los salientes a numeros
				{
					Integer idEnlace = this.lista.getUrlAId().get(enlace);
					if (idEnlace != null) {
						ids.add(idEnlace);
					}
				} // imprimir la lista unida por ###
					// String rdo = String.join(" ### ", ids); no se puede con arraylist de integers
				String rdo = String.join(" ### ", ids.stream().map(String::valueOf) // convertir int a String
						.toArray(String[]::new));
				writer.println(rdo);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addEnlaceYCrearFichero(String pNombre, String pWeb, String pEnlace) {
		Boolean tieneWeb = true;
		Boolean tieneEnl = true;
		if (!this.lista.getUrlAId().containsKey(pWeb)) // si no tiene la web
		{
			tieneWeb = false;
		}
		if (!this.lista.getUrlAId().containsKey(pEnlace)) // si no tiene el enlace
		{
			tieneEnl = false;
		}
		// añadir el enlace y crear el fichero
		this.lista.anadirEnlace(pWeb, pEnlace);
		//crearFicheroEnlaces(pNombre, this.lista.getLista());

		if (!tieneWeb) {
			System.out.println("Se ha añadido una nueva web, creado en fichero 'WebActualizado.txt'");
			crearFicheroWebs("WebActualizado.txt", this.lista.getLista());
		}

		if (!tieneEnl) {
			System.out.println(
					"Se ha añadido el enclace como una nueva web, creado en fichero 'EnlaceWebActualizado.txt'");
			crearFicheroWebs("EnlaceWebActualizado.txt", this.lista.getLista());
		}

		crearFicheroEnlaces(pNombre, this.lista.getLista());

	}

	public void cargarTodosArchivos(String rutaWeb, String rutaPal, String rutaEnl) throws IOException {
		System.out.println("\nCargando las webs...");
		this.lectorWebs(rutaWeb);

		System.out.println("\nCargando las palabras claves...");
		Palabras.getPalabras().lectorPalabras(rutaPal);

		System.out.println("\nCargando los enlaces salientes...");
		this.lectorSalientes(rutaEnl);

		System.out.println("\nAsociando cada palabras clave con webs y viceversa...");
		Palabras.getPalabras().asociarPalabraConWebs2(this.lista);

		//para archivos más pequeños
		//Palabras.getPalabras().imprimirPalabrasAWebs(); //palabra-> webs
		//this.lista.imprimirWebsEnlaces();//enlaces salientes de cada web
		/*for (Web web : this.lista.getLista().values()) 
	        {
	        System.out.println("\n" + web.toString());    
			web.imprimirPalClave(); //
	        }*/
		
		
		System.out.println("\nDatos cargados correctamente.");

	}
	//MÉTODO EXTRA PARA PRÁCTICA 3
	public void cargarArchivosGrafo(String rutaWeb, String rutaEnl) throws IOException {
		
		System.out.println("\nCargando las webs...");
		this.lectorWebs(rutaWeb);

		System.out.println("\nCargando los enlaces salientes...");
		this.lectorSalientes(rutaEnl);
		
		System.out.println("\nDatos cargados correctamente.");

	}

}
