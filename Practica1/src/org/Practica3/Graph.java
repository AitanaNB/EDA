package org.Practica3;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
	
	//TODO
	public ArrayList<String> estanConectados2(String a1, String a2) 
	{
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
	
	/*
	 * public int probar(int nTests)
	 * cont<-0
	 * for test in 1..nTests
	 * 		x<-random(0..nNodos)
	 * 		y<-lo mismo
	 * 		conectados(x,y)
	 * return (tiempoactual-cont)/1000
	 * 
	 * 
	 */

}
