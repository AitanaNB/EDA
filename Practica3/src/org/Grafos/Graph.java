package org.Practica3;

import java.util.HashMap;

public class Graph {
	
    HashMap<String, ArrayList<String>> enlaces;
	
	public void crearGrafo(ListaWebs lista){
		// Post: crea el grafo desde la lista de webs
		//       Los nodos son nombres de páginas web		
		
          // COMPLETAR CÓDIGO
	}
	
	public void print(){
	   for (String web : enlaces.keySet()){
		System.out.print("Element: " + web + " >>> ");
		for (String saliente : enlaces.get(web))  System.out.print(saliente + " ### ");
		
		System.out.println();
	   }
	}
	
	public boolean estanConectados(String a1, String a2){
		Queue<String> porExaminar = new LinkedList<String>();
		
		boolean enc = false;



               // COMPLETAR CÓDIGO
		
		return enc;

	}
	
	public ArrayList<String> estanConectados(String a1, String a2) {
	       
	       // COMPLETAR CÓDIGO
	       
	       return null;
	
	}
}
