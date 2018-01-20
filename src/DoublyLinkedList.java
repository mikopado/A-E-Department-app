import java.util.ArrayList;
import java.util.List;

// Implementation of IPriorityQueue using DoublyLinkedList. This class considers two end points: the head and the tail
// The list can be prioritize, which means that if any node in the list may change the priority value, the list will be reorder based on this.
// At the end the list will handle two list at once. From head to tail the list is a simple queue(FIFO) until priority is set to zero for any node.
// If any node changes priority value , the list is reordered. The modified node will be pushed to the back of the queue. The highest priority will be placed 
// at the tail of the queue. So at the end the head of the list will be the next Node that needs to be prioritize. From the tail of the list
// elements at highest priority will be placed first and so forth.
public class DoublyLinkedList implements IPriorityQueue<Node, Integer> {

	private Node head;	
	private Node tail;
	private int size;
	
	public DoublyLinkedList() {
		head = null;		
		size = 0;
		tail = null;
	}
	
	//Add new element to the linked list. 
	@Override
	public void enqueue(Integer data) {
		
		Node node = new Node(data);
		
		if(head == null) {// If list is empty set the the head and tail point to the node
			head = node;
			tail = node;
		}else {	// Otherwise add to the back of the list. But as there is the priority that makes things different, some conditions need to be checked
			
			if(tail.getPriority() == 0) {//Simple case, no nodes have the priority set, so add new node to the tail
				
				tail.setNext(node);
				node.setPrevious(tail);			
				tail = node;
				
			}else {// If tail has priority value set
				
				Node n = tail;
				
				if(n == head) {//This happens when only one node is in the list
					
					node.setNext(tail);
					node.setPrevious(null);
					tail.setPrevious(node);
					head = node;
					
				}else {// If more than one node in the list
					
					while(n.getPrevious().getPriority() != 0) {//Place the new node to the position where the node in front has priority equal to zero until finds any
						n = n.getPrevious();
						if(n == head) {
							break;
						}
					}
					if(n == head) {
						node.setNext(n);
						n.setPrevious(node);
						node.setPrevious(null);
						head = node;
						
					}else {
						// Just set the new node in the new position
						node.setPrevious(n.getPrevious());
						node.setNext(n);
						n.getPrevious().setNext(node);
						n.setPrevious(node);
					}
					
					
				}			
				
				
			}
			

		}
		
		++size;
	}
	
	@Override
	public boolean contains(Integer data) {
		
		return find(data) != null;
		
	}

	private Node find(Integer data) {// Find the node that holds the passing data
	
		Node node = head;
		
		while(node != null) {
			if(node.getData() != data) {				
				node = node.getNext();
			}else {
				break;
			}
		}		
		return node;		
		
	}
	@Override
	public void prioritize(Integer data, Integer priority) {// Prioritize the list. Set the priority to the found node and then reorder the list based on the new condition
		
		Node node = find(data);
		node.setPriority(priority);
		
		if(size() > 1) {
			reOrder(node);
		}
		
	}
	
	
	private void reOrder(Node node) {	
		
		if(node != tail) {
			
			Node next = node.getNext();
			
			if(next.getPriority() < node.getPriority()) {// If the node next to the selected node has priority less than the selected node, 
													//then swap nodes and keep doing that until a node with higher priority is found
				swapNodes(next, node);
				reOrder(node);
			}	
		}
		
	
	}
	
	private void swapNodes(Node next, Node current) {// Swap the position of nodes	
		
		if(size() > 2) {
			if(current == head) {//If the examined node is the head and there is more than one element in the list, so set the previous to null
				next.getNext().setPrevious(current);
				current.setNext(next.getNext());
				next.setNext(current);
				next.setPrevious(null);
				current.setPrevious(next);
				head = next;
			}else if(next == tail) {// Case when the next node is the last node, so avoid to set the next to null
				next.setPrevious(current.getPrevious());
				current.getPrevious().setNext(next);
				next.setNext(current);
				current.setPrevious(next);
				current.setNext(null);
				tail = current;
			}else {// If the the node in front of the examined node is not the last node 
				next.getNext().setPrevious(current);
				current.setNext(next.getNext());
				next.setNext(current);
				next.setPrevious(current.getPrevious());
				current.getPrevious().setNext(next);
				current.setPrevious(next);
			}
		}else {
			current.setPrevious(next);
			next.setNext(current);
			current.setNext(null);
			next.setPrevious(null);
			head = next;
			tail = current;
			
		}
		
		
	}
	
	@Override
	public List<Node> getQueue(){//Retrieve all elements of the queue from head to tail
		Node node = head;
		List<Node> list = new ArrayList<>();
		while(node != null) {
			list.add(node);
			node = node.getNext();
		}
		return list;
		
	}

	@Override
	public Node peek() {// Get but not remove the head of the queue. Useful to get the next element to be served
		return head;
	}
	
	@Override
	public Node priorityPeek() {//Get but not remove the tail. Useful to get the highest priority.
		return tail;
	}
	
	@Override
	public boolean dequeue() {// Remove Node from the queue
		
		int count = size();
		
		if(count > 1) {//If more than one node is in the list, just simply remove the tail of the list
			
			Node newTail = tail.getPrevious();
			newTail.setNext(null);
			tail = newTail;
			--size;
			
		}else {// If only one element in the list, set the size to zero and the head and tail to null
			head = null;
			tail = null;
			size = 0;
		}
		
		if(count > size()) {//Check if the operation has been successful
			
			return true;
		}
		
		return false;
		
	}
	
	@Override
	public int size() {
		return size;
	}

	
	
}
