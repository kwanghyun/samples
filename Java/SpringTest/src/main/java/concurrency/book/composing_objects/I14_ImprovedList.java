package concurrency.book.composing_objects;

import java.util.*;

import net.jcip.annotations.*;

/**
 * ImprovedList
 *
 * Implementing put-if-absent using composition
 *
 * There is a less fragile alternative for adding an atomic operation to an
 * existing class: composition. ImprovedList in Listing 4.16 implements the List
 * operations by delegating them to an underlying List instance, and adds an
 * atomic putIfAbsent method. (Like Collections.synchronizedList and other
 * collections wrappers, ImprovedList assumes that once a list is passed to its
 * constructor, the client will not use the underlying list directly again,
 * accessing it only through the ImprovedList.
 */
@ThreadSafe
public class I14_ImprovedList<T> implements List<T> {
	private final List<T> list;

	/**
	 * PRE: list argument is thread-safe.
	 */
	public I14_ImprovedList(List<T> list) {
		this.list = list;
	}

	public synchronized boolean putIfAbsent(T x) {
		boolean contains = list.contains(x);
		if (contains)
			list.add(x);
		return !contains;
	}

	// Plain vanilla delegation for List methods.
	// Mutative methods must be synchronized to ensure atomicity of putIfAbsent.

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<T> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public synchronized boolean add(T e) {
		return list.add(e);
	}

	public synchronized boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public synchronized boolean addAll(Collection<? extends T> c) {
		return list.addAll(c);
	}

	public synchronized boolean addAll(int index, Collection<? extends T> c) {
		return list.addAll(index, c);
	}

	public synchronized boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public synchronized boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public T get(int index) {
		return list.get(index);
	}

	public T set(int index, T element) {
		return list.set(index, element);
	}

	public void add(int index, T element) {
		list.add(index, element);
	}

	public T remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public synchronized void clear() {
		list.clear();
	}
}