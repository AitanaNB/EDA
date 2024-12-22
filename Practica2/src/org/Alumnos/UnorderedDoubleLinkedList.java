package org.Alumnos;

public class UnorderedDoubleLinkedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {
	//hija de doubeLL, implementa UnorderedListADT
	
	public void addToFront(T elem) {
	// anade un elemento al comienzo
		Node<T> nuevo= new Node<>(elem);
		if(this.isEmpty()) {
			super.first=nuevo;
			super.last=nuevo;
		}
		else {
			nuevo.next=super.first;
			first.prev=nuevo;
			first=nuevo;
		}
		count++;
	}

	public void addToRear(T elem) {
	// anade un elemento al final 
		Node<T> nuevo= new Node<>(elem);
		if(this.isEmpty()) {
			super.first=nuevo;
			super.last=nuevo;
		}
		else {
			last.next=nuevo;
			nuevo.prev=last;
			last=nuevo;
		}
		count++;
	}
	
	public void addAfter(T elem, T target) {
	// Anade elem detras de otro elemento concreto, target, que ya se encuentra en la lista
	// por lo que la lista no es vacia
		Node<T> actual=super.first;
		while(actual!=null && !actual.data.equals(target)) {
			actual=actual.next;
		}
		// una vez encontado el target, donde esta actual
		Node<T> anadir= new Node<>(elem);
		anadir.next=actual.next;
		anadir.prev=actual;
		// si no estamos en el ultimo nodo
		if(actual.next!=null){ 
			actual.next.prev=anadir; 
		}
		else { 
			last=anadir; 
		}
		actual.next=anadir;
		count++;	
	}
}

