package org.webEda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


//clase encargada de manejar los ficheros de las webs
public class Web {
	// atributos
	private String url;
	private ArrayList<String> enlacesSalientes;
	private ArrayList<String> palClave;


	// constructora
	public Web(String pUrl) {
		this.url = pUrl;
		this.enlacesSalientes = new ArrayList<>();
		this.palClave = new ArrayList<>();
		

	}

	// Getters
	public String getUrl() {
		return this.url;
	}

	public ArrayList<String> getSalientes() {
		return this.enlacesSalientes;
	}

	public ArrayList<String> getClaves() {
		return this.palClave;
	}

	// otros metodos

	public void imprimirEnlaces() {
		if (this.enlacesSalientes.isEmpty()) {
			System.out.println("Esta web no tiene enlaces salientes.");
		} else {
			for (String enlace : enlacesSalientes) {
				System.out.println("  - " + enlace);
			}
		}
	}

	public void addEnlace(String pEnlace) 
	{
		if (!this.enlacesSalientes.contains(pEnlace)) {
			this.enlacesSalientes.add(pEnlace);
		} else {
			System.out.println("Enlace ya existente");
		}

	}

	public void addPalClave(String pClave) {
		if (!this.palClave.contains(pClave)) {
			this.palClave.add(pClave);
		} else {
			//System.out.println("Palabra clave ya existente");
		}
	}

	@Override
	public String toString() {
		return this.url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Web web = (Web) o;
		return Objects.equals(this.url, web.url) && Objects.equals(this.enlacesSalientes, web.getSalientes())
				&& Objects.equals(this.palClave, web.getClaves());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.url, this.enlacesSalientes, this.palClave);
	}

	public void imprimirPalClave() {
		System.out.println("Las palabras claves son:");
		for (String p : this.palClave) {
			System.out.println(p);
		}
	}

}
