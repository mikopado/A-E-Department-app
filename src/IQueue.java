import java.util.List;
//Interface for FIFO data structures
public interface IQueue<T, E> {

	boolean dequeue();
	void enqueue(E element);
	boolean contains(E element);
	List<T> getQueue();
	T peek();
	int size();
}
