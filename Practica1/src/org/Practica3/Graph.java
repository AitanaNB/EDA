package org.Practica3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.webEda.ListaWebs;
import org.webEda.Web;
import org.webEda.WebManager;

public class Graph {
	HashMap<String, ArrayList<String>> enlaces;
	
	public Graph()
	{
		this.enlaces=new HashMap<>();
	}
	
	public void crearGrafo(ListaWebs lista)
	{
		// Post: crea el grafo desde la lista de webs
		//       Los nodos son nombres de paginas web		
		
		/*
		 * recorrer lista de webs y conseguir el string()
		 * añadir la lista de enlaces salientes x cada web
	
		
		for(Web actual:lista.getLista().values())
		{ // WebManager.getWebManager().getLista().getLista().values()
			this.enlaces.put(actual.getUrl(), actual.getSalientes());
		}*/
		
		//creado para JUnit
		for(Web web:lista.getLista().values())
		{
			String url=web.getUrl();
			ArrayList <String> salientes=web.getSalientes();
			enlaces.put(url, salientes);
		}
	}
	
	public void print(){
	   for (String web : enlaces.keySet()){
		System.out.print("Element: " + web + " >>> ");
		for (String saliente : enlaces.get(web))  System.out.print(saliente + " ### ");
		
		System.out.println();
	   }
	}
	
	public boolean estanConectados(String a1, String a2)
	{
		if (!enlaces.containsKey(a1) || !enlaces.containsKey(a2)) {
            return false; // Si alguna web no está en el grafo
        }
		
		Queue<String> porExaminar = new LinkedList<String>();
		HashSet<String> examinados = new HashSet<String>();
		
		porExaminar.add(a1);
		examinados.add(a1);
		boolean enc = false;
		
		while(!enc && !porExaminar.isEmpty()) {
			String act=porExaminar.remove();
			if (act.equals(a2)) {
				enc=true;
			} else {
				ArrayList<String> salientes=enlaces.get(act);
				for (String w: salientes) {
					if (!examinados.contains(w)) {
						examinados.add(w);
						porExaminar.add(w);
					}
				}
			}
		}
		return enc;
	}
	
	public ArrayList<String> estanConectados2(String a1, String a2) {
		if (!enlaces.containsKey(a1) || !enlaces.containsKey(a2)) {
            return null; // Si alguna web no esta en el grafo
        }
		
		Queue<String> porExaminar = new LinkedList<String>();
		HashSet<String> examinados = new HashSet<String>();
		HashMap<String, String> backPointer = new HashMap<String, String>();
		
		porExaminar.add(a1);
		examinados.add(a1);
		
		while(!porExaminar.isEmpty()) {
			String act=porExaminar.remove();
			if (act.equals(a2)) {
				ArrayList<String> camino = new ArrayList<String>();
				String nodo=a2;
				while (nodo!=null) {
					camino.add(0,nodo);
					nodo=backPointer.get(nodo);
				}
				return camino;
			}
			
			ArrayList<String> salientes=enlaces.get(act);
			for (String w: salientes) {
				if (!examinados.contains(w)) {
					examinados.add(w);
					porExaminar.add(w);
					backPointer.put(w, act);
				}
			}
		}
		return null;
	}
	
	//************************ PRÁCTICA 4 ************************
	public HashMap<String, Double> calcularRandomWalkRank(int nTests) {
		double d = 0.85; // damping factor
		Random r = new Random();
		HashMap<String, Double> result=new HashMap<String, Double>();
		
		if (enlaces.isEmpty()) {return null;} //devuelve null si el grafo esta vacio
		
		for (String web : enlaces.keySet()){
			result.put(web, 0.0); //contar cuántas veces se visita cada nodo
		}
		
		for (int i=0; i<nTests; i++) 
		{
			Object[] claves = enlaces.keySet().toArray(); // convierte las claves en un array para acceder mediante indice
			int indiceAleatorio = r.nextInt(enlaces.size()); // generar un numero entero aleatorio entre 0 y n (tamano de enlaces)
			String actual = (String) claves[indiceAleatorio]; // accede al elemento que corresponde al indice aleatorio
			
			HashSet<String> examinados = new HashSet<String>();
			boolean fin = false; // termina el recorrido tambien cuando la pagina es visitada
			
			while(r.nextDouble()<=d && !fin) {
				result.put(actual, result.get(actual)+1);
				if (examinados.contains(actual)) { // si contiene ya la pagina sale
					fin=true;
				} else {
					examinados.add(actual);
					ArrayList<String> salientes = enlaces.get(actual);
				
					if (salientes.size()==0) {
						fin = true;
					} else {
						actual = (String) salientes.get(r.nextInt(salientes.size()));
					}
				}
				
			}
		}
		return result;

     }
	
	 public HashMap<String, Double> calcularPageRank() 
	 {
			/*boolean trace = false; // tracing the pagerank algorithm
			boolean damping = true; // tracing the pagerank algorithm
			*/
			double dampingFactor = 0.85; // Factor de amortiguación
			double epsilon = 0.0001; // Umbral para convergencia
			
			HashMap<String, Double> pageRank=new HashMap<>();
			HashMap<String, Double> nuevoRank=new HashMap<>(); //guardar valores para epsilon
			
			if (enlaces.isEmpty())
			{
				return null;
			}
			
			//Inicializar PageRank con valores iguales
			int numNodos= enlaces.size();
			for(String nodo :enlaces.keySet())
			{
				pageRank.put(nodo, 1.0/numNodos);
			}
			
			boolean convergencia=false;
			while(!convergencia)
			{
				//resetear
				for(String nodo:enlaces.keySet())
				{
					nuevoRank.put(nodo, (1-dampingFactor)/numNodos);
				}
				
				//Calcular contribuciones de cada nodo
				for(String nodo:enlaces.keySet())
				{
					double prActual=pageRank.get(nodo);
					ArrayList<String> salientes=enlaces.get(nodo);
					
					if(salientes.isEmpty())
					{//Nodo sin enlaces salientes-> PR uniforme
						for(String destino: salientes)
						{	double aux=nuevoRank.get(destino); // (1-d)/N
							nuevoRank.put(destino, aux + dampingFactor * (prActual / numNodos));
						}
					}
					else
					{//Nodo con enlaces salientes
						for(String destino: salientes)
						{	double aux=nuevoRank.get(destino); // (1-d)/N
							nuevoRank.put(destino, aux + dampingFactor * (prActual / salientes.size()));
						}
					}
				}
				
				//Covergencia: Delta valores PageRank menores a Epsilon
				double delta= 0.0;
				for (String nodo:pageRank.keySet())
				{
					double dif=pageRank.get(nodo)-nuevoRank.get(nodo);
					if(dif<0)
					{
						dif=-dif;
					}
					delta += dif;
				}
				if(delta<epsilon)
				{
					convergencia=true;
				}
				//Actualizar PageRank
				pageRank.putAll(nuevoRank);
			}
			return pageRank;
	     }
	     
	     
	     public void imprimirLosDeMejorPageRank(double[] pr, int n, String[] id2String) { // inefficient but valid!
	     // Post: imprime los n elementos de mayor valor.
	     //       Es ineficiente porque calcula los maximos consecutivamente, y borra el maximo anterior, es decir, borra los valores de entrada
	     //       Puede ser util para visualizar los resultados
			for (int x = 1; x <= n; x++) {
				double max = -1;
				int iMax = -1;
				for (int j = 0; j < pr.length; j++)
					if (pr[j] > max) {
						max = pr[j];
						iMax = j;
					}
				System.out.println("The " + x + "th best element is "
						+ id2String[iMax] + " with value " + max);
				pr[iMax] = -1; // delete the maximum
			}
		}
	     
	     
	     /*public ArrayList<Par> buscarPaginas(String palabraClave1, String palabraClave2) 
	     {
			return null;
	    	 
	     }
	     */
}
