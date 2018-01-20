import java.util.Set;
import java.util.function.Predicate;

//Interface for ORM, declaring the most common operation for managing object into database
public interface IOrm<T> {

	Set<T> find(Predicate<T> func);
	Set<T> findAll();
	T findById(int id);
	boolean delete(int id);
	boolean add(T element);
	boolean update(T element);
	void save(T element);
	boolean contains(int id);
	
}
