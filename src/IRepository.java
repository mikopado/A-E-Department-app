import java.util.Set;
import java.util.function.Predicate;

//Interface that handles communication between domain classes to the ORM
public interface IRepository<T> {
	
	Set<T> getAll();
	T getElementById(int id);
	Set<T> getElements(Predicate<T> func);
	void save(T element);
	boolean add(T element);
	boolean edit(T element);
	boolean remove(int id);
	boolean contains(int id);
	
}
