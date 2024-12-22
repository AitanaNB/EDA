package org.webEda;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ListaWebs 
{
	//atributos
	private HashMap<Integer, Web> lista;
	private HashMap<String, Integer> urlAId;
	
	
	
	//constructora
	/**
	 * post: inicializa la lista de webs a un HashMap vacio
	 * 
	 */
	public ListaWebs()
	{
		this.lista = new HashMap<Integer, Web>();
		this.urlAId = new HashMap<String, Integer>();
	}
	
	//otros metodos
	
	public HashMap<Integer, Web> getLista()
	{
		return this.lista;
	}
	public HashMap<String, Integer> getUrlAId()
	{
		return this.urlAId;
	}
	
	
	
	
	
	/**
	 * 
	 * @param pId
	 * @param pWeb
	 * 
	 * 
	 * post: anade a ListaWebs una pagina web
	 * 
	 */
	public void anadir(int pId, Web pWeb) //modificado para que tenga en cuenta que el nombre puede existir
	{	
		
		if (!this.lista.containsKey(pId) && !this.urlAId.containsKey(pWeb.toString()))
		{
			this.lista.put(pId, pWeb);
			this.urlAId.put(pWeb.getUrl(), pId);
		}
		else
		{
			System.out.println("Error: Ya existe una web con Id: " +
		pId + " o ya existe una web con nombre: " + pWeb.toString());
		}

	}
	
	public void anadirEnlace (String pWeb, String pEnlace)
	{
		if(!this.urlAId.containsKey(pWeb)) //añadir web si no está
		{
			this.anadir(this.numeroWebs(), new Web(pWeb));
		}
		
		int idPrinc= this.urlAId.get(pWeb); //conseguir el id
		
		if(!this.urlAId.containsKey(pEnlace))//añadir enlace a las webs si no está
		{
			this.anadir(this.numeroWebs(), new Web(pEnlace));
		}
		
		//ArrayList<String> enlaces = this.lista.get(idPrinc).getSalientes(); //conseguir los enlaces
		//enlaces.add(pEnlace);//añadir el nuevo a la lista
		this.lista.get(idPrinc).addEnlace(pEnlace);
	}
	/* no hace falta, se hace directamente en clase Palabras
	public void asociarWebsConPalClave() throws IOException { //al revés
		HashMap<String, String> diccionario= Palabras.getPalabras().getDiccionario2();
		
		for (String web : this.urlAId.keySet()) // todas las urls
		{ // iterador externo
			
			for (int i = 0; i < web.length(); i++)// divide las urls
			{ // iterador interno
				for (int j = i; j < web.length(); j++) 
				{
					String substring = web.substring(i, j+1);
					if (diccionario.containsKey(substring)) // si parte de la url está en diccionario
					{
						this.lista.get(this.urlAId.get(web)).addPalClave(substring);
					}	//añade ese substring que está en el diccionario a listaPalClave
				}
			}
		}
	}*/
	
	/**
	 * 
	 * @param id
	 * 
	 * pre: x es un valor entero>=0
	 * post: devuelve la web asociada a id
	 * 
	 */
	private String ident2String(int id)
	{
		Web w=this.lista.get(id);
		String nombre;
		if (w!=null)
		{
			nombre = w.getUrl();
		}
		else
		{
			nombre = null;
		}
		
		return nombre;
	}
	
	/**
	 * 
	 * @param web
	 * 
	 * Post: dado el nombre de una web, devuelve las páginas webs a las que hace referencia
	 * 
	 */
	public ArrayList<String> salientes(String webUrl)
	{
		ArrayList<String> salientes= new ArrayList<String>();
		if (this.urlAId.containsKey(webUrl))
		{
			int id=this.urlAId.get(webUrl);
			Web w=this.lista.get(id);
			if (w!=null)
			{
				return w.getSalientes();
			}
		}
		
		
		return salientes;
		
	}
	
	/**
	 * 
	 * @return una lista ordenada alfabeticamente de las paginas web
	 * 
	 * Post: -
	 * Pre: 
	 */
	public ArrayList<String> websOrdenadas()
	{
		ArrayList<String> websOrdenadas=new ArrayList<String>();
		
		for (Web w: this.lista.values()) //pasa las webs a un nuevo arraylist
		{
			websOrdenadas.add(w.getUrl());
		}
		
		this.quickSort(websOrdenadas, 0, websOrdenadas.size()-1);
		
		return websOrdenadas; //devuelve la lista ordenada
	}
	
	private void quickSort(ArrayList<String> lista, int izq, int der)
	{
		if (izq<der) 
		{
            int i = izq; //recorre desde la izquierda del vector
            int j = der;
            String pivote = lista.get((izq + der)/2); //eleccion del pivote (elemento central)
            String aux;
            
            //la particion termina cuando i>j
            while (i<=j) 
            {
                //encontrar el primer elemento a la izquierda que es mayor que el pivote
                while (lista.get(i).compareTo(pivote)<0) 
                {
                    i++; //avanza en la lista
                }
                //encontrar el primer elemento a la derecha que es menor que el pivote
                while (lista.get(j).compareTo(pivote)>0) 
                {
                    j--; //retrocede en la lista
                }
                //intercambiar los elementos mal ubicados
                if (i<=j) 
                {
                    aux = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, aux);
                    i++;
                    j--;
                }
            }

            //hacer la particion de los subvectores izquierdo y derecho
            if (izq<j) 
            {
                quickSort(lista, izq, j); //ordenar la mitad izquierda
            }
            if (i<der) 
            {
                quickSort(lista, i, der); //ordenar la mitad derecha
            }
        }
    }
	
	/**
	 * 
	 * @param palClave
	 * @return una lista de paginas web que contiene la palabra clave palClave
	 */
	public ArrayList<String> word2Webs(String s) 
	{
		
		ArrayList<String> websConClave = new ArrayList<String>();
        Palabras palabras=Palabras.getPalabras();
        if(palabras.getDiccionario2().containsKey(s))
        //if (palabras.getDiccionario2().containsKey(s))
        {
        	websConClave=palabras.getPalabrasAWebs().get(s);
        }
        else
        {
        	System.out.println("No existe dicha palabra en el diccionario");
        }
        
        
        return websConClave;
	}
	
	
	/**
	 * 
	 * @param web
	 * @return una lista de palabras que aparece en la web
	 */
	public ArrayList<String> web2Words(String webUrl)
	{
		ArrayList<String> listaPal=new ArrayList<String>();
		int id=this.urlAId.get(webUrl);
		Web w=this.lista.get(id);
		if (w!=null)
		{
			listaPal=w.getClaves();
		}
		return listaPal;
	}
	
	public void borrarWeb(int id)
	{
		if (this.lista.containsKey(id))
		{
			String nombre=this.ident2String(id);
			this.lista.remove(id);
			this.urlAId.remove(nombre);
		}
		else
		{
			System.out.println("No existe web con id: " +id);
		}
		
	}

	
	public void imprimir()
	{
		this.lista.entrySet().stream()
		.forEach(entry-> System.out.println("Id: " + entry.getKey() + 
				", Nombre web: " + entry.getValue()));
	}
	
	public void imprimirWebsEnlaces() 
	{
        for (Map.Entry<Integer, Web> entry : lista.entrySet()) 
        {
            Integer id = entry.getKey();
            Web web = entry.getValue();

            System.out.println("\nId: " + id + ", Web: " + web);
            System.out.println("Enlaces:");

            web.imprimirEnlaces();
        }
    }
	
	public int numeroWebs()
	{
		return this.lista.size();
	}
	
	
	
}

