package org.Alumnos;

import java.util.Iterator;
//import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements ListADT<T> {

	// Atributos
	protected Node<T> first; // apuntador al primero
	protected Node<T> last; // apuntador al ultimo
	protected String descr; // descripcion
	protected int count;

	// Constructor
	public DoubleLinkedList() {
		first = null;
		last = null;
		descr = "";
		count = 0;
	}

	public void setDescr(String nom) {
		descr = nom;
	}

	public String getDescr() {
		return descr;
	}

	public T removeFirst() // O(1)
	{
		// Elimina el primer elemento de la lista
		// Precondicion:-
		if (this.first == null) // tambien con isEmpty()
		{// si la lista esta vacia
			return null;
		} else {
			T rdo = first.data;
			first = first.next;
			if (first == null) {// si solo tiene un elemento
				last = null;
			} else {// si tiene mas de un elemento
				first.prev = null;
			}
			this.count--;
			return rdo;
		}
	}

	public T removeLast() // O(1)
	{
		// Elimina el ultimo elemento de la lista
		// Precondicion:-
		if (this.isEmpty()) {
			return null;
		} else {
			T rdo = last.data;
			last = last.prev;
			if (last == null) {
				first = null;
			} else {
				last.next = null;// eliminar el ultimo
			}
			this.count--;
			return rdo;
		}
	}

	public T remove(T elem) // O(n) en el peor de los casos
	{
		// Elimina un elemento concreto de la lista (primera aparicion)

		if (isEmpty()) {
			return null;
		}

		Node<T> actual = this.first;
		Boolean enc = false;

		while (actual != null && !enc) {
			if (actual.data.equals(elem)) {
				enc = true;
			} else {
				actual = actual.next;
			}
		}
		if (enc) { // si el elemento existe
			if (actual == this.first && actual == this.last) {// si solo hay un elemento
				this.first = null;
				this.last = null;
				this.count--;
			} else if (actual == this.last) { // si el elem es el ultimo
				this.removeLast();
			} else if (actual == this.first) { // si elem es el primero
				this.removeFirst();
			} else {
				actual.prev.next = actual.next;
				actual.next.prev = actual.prev;
				this.count--;
			}
			return actual.data;
		} else {
			return null;
		}
	}

	public void removeAll(T elem) // O(n)
	{
		// Elimina todas las apariciones de elem

		Node<T> actual = this.first;
		while (actual != null) {
			if (actual.data.equals(elem)) {
				Node<T> aux = actual.next;// crear aux para no perder info
				this.remove(actual.data);
				actual = aux;
			} else {
				actual = actual.next;
			}
		}
	}

	public T first() // O(1)
	{
		// Da acceso al primer elemento de la lista
		if (isEmpty())
			return null;
		else
			return first.data;
	}

	public ListADT<T> clone() {
		// Devuelve una copia de la lista (copia todos los nodos)
		DoubleLinkedList<T> copia = new DoubleLinkedList<>();

		if (!this.isEmpty()) {
			Node<T> actualOg = this.first; // puntero al primero de la lista
			Node<T> nuevoNodo = new Node<>(actualOg.data); // guardar nodo con contenido
			copia.first = nuevoNodo;// puntero de copia a nuevo elem

			Node<T> actualCopia = copia.first;// nuevo nodo añadido en copia
			actualOg = actualOg.next;// avanzar en lista original

			while (actualOg != null) { // actualizar nodo nuevo con datos de OG
				nuevoNodo = new Node<>(actualOg.data);
				actualCopia.next = nuevoNodo;
				nuevoNodo.prev = actualCopia;

				// avanzar con los punteros;
				actualCopia = nuevoNodo;
				actualOg = actualOg.next;
			}
			copia.last = actualCopia;
			copia.count = this.count;
		}
		return copia;
	}

	public T last() // O(1)
	{
		// Da acceso al último elemento de la lista
		if (isEmpty()) {
			return null;
		} else {
			return last.data;
		}
	}

	public boolean contains(T elem) {
		// Determina si la lista contiene un elemento concreto
		return this.find(elem) != null;
	}

	public T find(T elem) // O(n) en el peor de los casos
	{
		// Determina si la lista contiene un elemento concreto, y devuelve su
		// referencia, null en caso de que no esta
		Node<T> actual = this.first;
		while (actual != null) {
			if (actual.data.equals(elem)) {
				return actual.data;
			}
			actual = actual.next;
		}
		return null;
	}

	public boolean isEmpty() // O(1)
	// Determina si la lista esta vacia
	{
		return first == null;
	}

	public int size() // O(1)
	// Determina el numero de elementos de la lista
	{
		return count;
	}

	/** Return an iterator to the stack that iterates through the items . */
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator implements Iterator<T> {
		private Node<T> actual = first; // Inicializar

		public boolean hasNext() {
			return actual != null;
		}

		public T next()// recorre la lista
		{
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T contenido = actual.data;
			actual = actual.next;
			return contenido;
		}
	} // private class

	public void visualizarNodos() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		String result = new String();
		Iterator<T> it = iterator();
		result = "[ ";
		while (it.hasNext()) {
			T elem = it.next();
			result = result + elem.toString() + " ";
		}
		result = result + "]";
		return "DoubleLinkedList " + result;
	}

}
