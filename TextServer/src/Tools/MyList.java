package Tools;

/*
 * doppeltverkettete Liste
 */
public class MyList<T> {
	private class Node<T> {
		private Node next = null, 
			         prev = null;
		private T value = null;
		
		public Node(Node p, Node n, T v){
			next = n;
			prev = p;
			value = v;
		}
		
		public Node getNext(){return next;}
		
		public Node getPrev(){return prev;}
		
		public void setNext(Node n){next = n;}
		
		public void setPrev(Node p){prev = p;}
		
		public T getVal(){ return value;}
		
		public void setValue(T t) { this.value = t;}
	}
	
	private Node first,current,last;
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
			Node n = new Node(last,null,v);
			last.setNext(n);
		} else {
			first = new Node(null,null,v);
			current = first;
		}
	}
	
	/*
	 * Removes Current if hasCurrent
	 */
	public void remove(){
		if(!isEmpty() && hasCurrent()){
			Node p = current.getPrev();
			Node n = current.getNext();
			
			p.setNext(n);
			n.setPrev(p);
		}
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
	 * 
	 */
	public T getVal(){
		if(!isEmpty() && hasCurrent()) {
			return (T) current.getVal();
		}
		
		return null;
	}
	
	
	/*
	 *  current auf das nächste Objekt
	 */
	public void next(){
		current = current.getNext();
	}
	
	/*
	 * current auf das vorherige Objekt
	 */
	public void prev(){
		current = current.getPrev();
	
}
