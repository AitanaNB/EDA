package org.Alumnos;

public interface OrderedListADT<T>  extends ListADT<T> {
	
	public void add(T elem);
	// A�ade un elemento a la lista (en el lugar de orden que le corresponde)

	public void merge(DoubleLinkedList<T> zerrenda);

}
