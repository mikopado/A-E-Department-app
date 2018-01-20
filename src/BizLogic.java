
import java.util.Set;
import java.util.function.Predicate;

//Implementation of IRepository
public class BizLogic<T> implements IRepository<T> {

	private IOrm<T> orm;
	
	public BizLogic(IOrm<T> orm){
		this.orm = orm;
	}
	@Override
	public Set<T> getAll() {
		
		return orm.findAll();
	}

	@Override
	public T getElementById(int id) {

		return orm.findById(id);
	}
	
	@Override
	public Set<T> getElements(Predicate<T> func){
		return orm.find(func);
	}

	@Override
	public void save(T element) {
		
		orm.save(element);
	}

	@Override
	public boolean add(T element) {
		
		return orm.add(element);
	}

	@Override
	public boolean edit(T element) {
		
		return orm.update(element);
	}

	@Override
	public boolean remove(int id) {
		
		return orm.delete(id);
	}
	
	@Override
	public boolean contains(int id) {
		return orm.contains(id);
	}

}
