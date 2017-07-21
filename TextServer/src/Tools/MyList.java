package Tools;

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
	
	private Node first,current;
	
	public MyList() {
		first = null;
		current = null;
	}
	
	/*
	 * returns first == null
	 */
	public boolean isEmpty(){
		return first == null;
	}
	
	public boolean hasCurrent(){
		return current != null;
	}
	
	public T getVal(){
		if(!isEmpty() && hasCurrent()) {
			return (T) current.getVal();
		}
		
		return null;
	}
	
	public void next(){
		current = current.getNext();
	}
}
