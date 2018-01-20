
public class Node {
	
	private int priority;
	private int data;
	private Node previous;
	private Node next;
	
	public Node(int data) {
		
		setData(data);
		setPriority(0);
		setPrevious(null);
		setNext(null);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
}
