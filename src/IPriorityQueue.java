// Interface that extends the IQueue interface and adds two specific methods to handle priority queues
public interface IPriorityQueue<T, E> extends IQueue<T, E> {

	void prioritize(E data, Integer priority);	
	T priorityPeek();
}
