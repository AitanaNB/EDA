package org.Alumnos;

public class OrderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {
	
	public void add(T elem) { 
		
		Node<T> nuevo= new Node<>(elem);
		Comparable<T> comp = (Comparable<T>) elem;
		
		if (isEmpty())
		{
			first=nuevo;
			last=nuevo;
		}
		else {
			Node<T> actual=first;
			Node<T> anterior=null;
			
			//comparar en la lista
			while (actual!=null && comp.compareTo(actual.data)>0) 
			{
				anterior=actual;
				actual=actual.next;
			}
			
			//insertar al inicio
			if (anterior==null) 
			{
				nuevo.next=first;
				first.prev=nuevo;
				first=nuevo;
			}
			//insertar al final
			else if (actual==null) 
			{
				last.next=nuevo;
				nuevo.prev=last;
				last=nuevo;
			}
			//insertar en el medio
			else 
			{
				anterior.next=nuevo;
				nuevo.next=actual;
				actual.prev=nuevo;
				nuevo.prev=anterior;
			}
		}
		count++;
	}


	public void merge(DoubleLinkedList<T> lista) {
		if (lista.isEmpty()) { return; } // si la lista de entrada vacía, devuelve la lista actual

		if (this.isEmpty()) { // si la lista actual es vacía, copia todos los nodos de la lista entrada
			Node<T> actual=lista.first;
			Node<T> ultimo=null; 

			while (actual!=null) { 
				if (actual==lista.first) { 
					first=actual; 
					ultimo=first; 
				} else {
					ultimo.next=actual; 
					ultimo=actual; 
				} 
				actual=actual.next; 
			} 
			this.count=lista.count; 
		} else {
			Node<T> actual1=first; // puntero para la lista actual
			Node<T> actual2=lista.first; // puntero para la lista de entrada
			Node<T> cabeza=null;
			Node<T> ultimo=null;
			Node<T> nuevo;
			
			while (actual1!=null &&actual2!=null) {
				if (((Comparable<T>) actual1.data).compareTo(actual2.data)<0){
					nuevo=new Node<>(actual1.data);
					actual1=actual1.next;
				} else {
					nuevo=new Node<>(actual2.data);
					actual2=actual2.next;
				}
				if (cabeza==null) {
					cabeza=nuevo;
					ultimo=nuevo;
				} else {
					ultimo.next=nuevo;
					nuevo.prev=ultimo;
					ultimo=nuevo;
				}
			}
			
			// si queda elementos de la lista actual, agregar los restantes
			while (actual1!=null) {
				nuevo=new Node<>(actual1.data);
				ultimo.next=nuevo;
				nuevo.prev=ultimo;
				ultimo=nuevo;
				actual1=actual1.next;
			}
			
			// si queda elementos de la lista entrada, agregar los restantes
			while (actual2!=null) {
				nuevo=new Node<>(actual2.data);
				ultimo.next=nuevo;
				nuevo.prev=ultimo;
				ultimo=nuevo;
				actual2=actual2.next;
			}
			
			first=cabeza;
			last=ultimo;
			count=count+lista.count;
		}
		
	}
	

}
