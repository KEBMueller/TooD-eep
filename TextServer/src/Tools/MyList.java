package Tools;

/*
 * doppeltverkettete Liste
 */
public class MyList<T> {
	private class Node<T> {
		private Node<T> next = null, 
			         prev = null;
		private T value = null;
		
		public Node(Node<T> p, Node<T> n, T v){
			next = n;
			prev = p;
			value = v;
		}
		
		public Node<T> getNext(){return next;}
		
		public Node<T> getPrev(){return prev;}
		
		public void setNext(Node<T> n){next = n;}
		
		public void setPrev(Node<T> p){prev = p;}
		
		public T getVal(){ return value;}
		
		public void setValue(T t) { this.value = t;}
	}
	
	private Node<T> first,current,last;
	private int size = 0;
	
	/*
	 * everything null
	 */
	public MyList() {
		first = null;
		current = null;
		last = null;
	}
	
	/*
	 * adds new Node at end
	 */
	public void add(T v){
		if(!isEmpty()){
			Node<T> n = new Node<T>(last,null,v);
			last.setNext(n);
		} else {
			first = new Node(null,null,v);
			current = first;
		}
		size++;
	}
	
	/*
	 * Removes Current if hasCurrent
	 */
	public void remove(){
		if(!isEmpty() && hasCurrent()){
			Node<T> p = current.getPrev();
			Node<T> n = current.getNext();
			current = n;
			p.setNext(n);
			n.setPrev(p);
		}
		size--;
	}
	
	/*
	 * returns first == null
	 */
	public boolean isEmpty(){
		return first == null;
	}
	
	
	 /*
	  *  current != null
	  */
	public boolean hasCurrent(){
		return current != null;
	}
	
	
	/*
	 * ---
	 */
	public T getObj(){
		if(!isEmpty() && hasCurrent()) {
			return (T) current.getVal();
		}
		
		return null;
	}
	
	/*
	 * Returns current val and removes current;
	 */
	public T pop(){
		if(this.hasCurrent()){
			T e = current.getVal();
			this.remove();
			return e;
		}
		return null;
	}
	
	
	 /*
	  * ---
	  */
	public void toFirst(){
		current = first;
	}
	
	
	/*
	 *  current auf das nächste Objekt
	 */
	public void next(){
		if(this.hasCurrent()){
			current = current.getNext();
		}
	}
	
	/*
	 * current auf das vorherige Objekt
	 */
	public void prev(){
		if(this.hasCurrent()) {
			current = current.getPrev();
		}
	}
	
	//Getters'n Setters
	public int getSize(){return size;}
	
}
