package org.webEda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Palabras // MAE solo hay un diccionario
{
	private static Palabras miPalabras = new Palabras();
	// private HashMap<Integer, String> diccionario= new HashMap<>(); //ID-->palabra
	// clave
	private HashMap<String, ArrayList<String>> palabraAWebs = new HashMap<>();
	// palabra-->lista de urls
	//private HashSet<String> diccionario=new HashSet<>();
	private HashMap<String, String> diccionario2 = new HashMap<>();

	public static Palabras getPalabras() {
		return miPalabras;
	}

	/*
	 * public void imprimir() { this.diccionario.entrySet().stream()
	 * .forEach(entry-> System.out.println("Clave: " + entry.getKey() +
	 * ", Palabra: " + entry.getValue())); }
	 */

	/*
	 * public HashMap<Integer, String> getDiccionario() { return this.diccionario; }
	 * 
	 * private void anadir(int pId, String pPalabra) { this.diccionario.put(pId,
	 * pPalabra); }
	 */

	public HashMap<String, ArrayList<String>> getPalabrasAWebs() {
		return this.palabraAWebs;
	}

	public HashMap<String, String> getDiccionario2() {
		return this.diccionario2;
	}

	/*
	 * public HashSet<String> getDiccionario() { return this.diccionario; }
	 * 
	 * public void asociarPalabraConWebs(ListaWebs listaWebs) throws IOException {
	 * //recorremos todas las webs de ListaWebs for (Web web :
	 * listaWebs.getLista().values()) { String urlWeb = web.getUrl(); // URL
	 * completa de la web
	 * 
	 * //comprobamos si alguna palabra clave está contenida en el URL for (String
	 * palabraClave : this.diccionario) { if (urlWeb.contains(palabraClave)) {
	 * //verificar si la URL contiene la palabra clave
	 * 
	 * //asociar la web a la palabra clave en el HashMap palabraAWebs
	 * palabraAWebs.get(palabraClave).add(urlWeb);
	 * 
	 * 
	 * // Añadir la palabra clave al conjunto de palabras clave de la web
	 * web.addPalClave(palabraClave);
	 * 
	 * //System.out.println("Asociando la palabra clave '" + palabraClave +
	 * "' con la web: " + urlWeb); } } }
	 * 
	 * 
	 * }
	 */
	public void asociarPalabraConWebs2(ListaWebs listaWebs) throws IOException {
		for (String web : listaWebs.getUrlAId().keySet()) // todas las urls
		{ // iterador externo
			for (int i = 0; i < web.length(); i++)// divide las urls
			{ // iterador interno
				for (int j = i; j < web.length(); j++) 
				{
					String substring = web.substring(i, j+1);
					if (diccionario2.containsKey(substring)) // si parte de la url está en diccionario
					{
						ArrayList<String> websAsociadas = palabraAWebs.get(substring);
						if (websAsociadas == null) {
							websAsociadas = new ArrayList<>(); // Inicializa la lista
							palabraAWebs.put(substring, websAsociadas); // Almacena la nueva lista en el HashMap
						}

						websAsociadas.add(web); //asociar web a la palabra
						listaWebs.getLista().get(listaWebs.getUrlAId().get(web)).addPalClave(substring); //asociar palabra clave a web
					}
				}
			}
		}
	}

	/*
	 * public void lectorPalabras (String pNombre) throws IOException { try
	 * (BufferedReader lector = new BufferedReader(new FileReader(pNombre))) {
	 * //crear un BF a traves de fileReader, que lee un fichero nombre x int cont=0;
	 * // Leer el archivo diccionario y cargar palabras String actual;
	 * 
	 * while ((actual = lector.readLine()) != null) { //formato: palabra
	 * 
	 * //ignorar las lineas vacias, x si se eliminan webs if
	 * (actual.trim().isEmpty()) { continue; } this.anadir(cont, actual); cont++; }
	 * } catch (IOException e) //excepciones de entrada/salida {
	 * e.printStackTrace(); } //this.imprimir(); }
	 */

	public void lectorPalabras(String pNombre) throws IOException {
		try (BufferedReader lector = new BufferedReader(new FileReader(pNombre))) {
			// crear un BF a traves de fileReader, que lee un fichero nombre x
			// leer cada palabra clave del archivo
			String actual;

			while ((actual = lector.readLine()) != null) {
				String palabra = actual.trim();
				if (!palabra.isEmpty() && !palabraAWebs.containsKey(palabra)) {

					// this.diccionario.add(palabra);
					this.diccionario2.put(palabra, palabra);
					// inicializa una lista vacía para cada palabra clave
					palabraAWebs.put(palabra, new ArrayList<>());
				}
			}
		} catch (IOException e) // excepciones de entrada/salida
		{
			e.printStackTrace();
		}

	}

	public void anadirPalabrasADiccionario(String pPalabra) {
		// this.diccionario.add(p);
		this.diccionario2.put(pPalabra, pPalabra);
		this.palabraAWebs.put(pPalabra, new ArrayList<String>());
	}

	public void imprimirDiccionario() {
		this.diccionario2.entrySet().stream()
				.forEach(entry -> System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue()));
	}

	public void imprimirPalabrasAWebs() {
		for (String palabra : palabraAWebs.keySet()) {
			ArrayList<String> webs = palabraAWebs.get(palabra);
			System.out.println("Palabra: " + palabra + " -> Webs: " + webs);
		}
	}
	
	public void resetear()
	{
		this.palabraAWebs.clear();
		this.diccionario2.clear();
	}

}