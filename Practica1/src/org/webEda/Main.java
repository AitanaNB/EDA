package org.webEda;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		WebManager webManager = WebManager.getWebManager();
        
		long startTime = System.nanoTime();
        String rutaWeb="F:\\Docs\\index-2024-25";
        String rutaPal="F:\\Docs\\words.txt";
        String rutaEnl="F:\\Docs\\pld-arcs-1-N-2024-25";
        
        webManager.cargarTodosArchivos(rutaWeb, rutaPal, rutaEnl);
        long tiempoLectura= System.nanoTime();
        long tiempoEj= tiempoLectura- startTime;
        System.out.println("El tempo de lectura de los tres archivos es: " +tiempoEj/1000000 + " milisegundos");
        
        //webManager.imprimir(); //para ver si las webs se ha cargado correctamente
    }


}
