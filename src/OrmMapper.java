import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


// Implementation of IOrm interface. This class handles the communication to the database, avoiding the domain calsses to directly query the database
public class OrmMapper<T> implements IOrm<T> {

	private IDatabase db;
	private Class<T> clazz;
	private String tableName;

	
	public OrmMapper(IDatabase db, Class<T> type) throws SQLException {
		
		this.db = db;
		clazz = type;
		tableName = clazz.getSimpleName() + "s";
		createTable();
		
	}
	
	//Create table into database
	public void createTable() throws SQLException {
		
		try (Connection conn = db.connect()) {			
	              
	        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
	        
	        for(Class<?> c = clazz; c != null; c= c.getSuperclass()) {//Allows to consider also inherited fields that the current class has
	        	
	        	for(Field field : c.getDeclaredFields()) {//Get declared fields allows to retrieve either public or private fields from the class
	        		
		        	field.setAccessible(true); // set field accessible to allow to get values from private fields
		        	String name = field.getName();
			        
			        if(name.equals("id")) {
			        	
			        	query.append(" id INTEGER PRIMARY KEY AUTOINCREMENT");
			        	
			        } else{
			        	
		        		query.append(", " + name);		        	
			        	
			        	switch(field.getType().toString()) {
			        	
				        	case "class java.lang.String":
				        		
				        		query.append(" VARCHAR(400) NOT NULL ");
				        		break;
				        		
				        	case "class java.lang.Integer":
				        		//Consider the case of foreign key
				        		if((name.contains("id") || name.contains("ID") || name.contains("Id")) && name.length() > 2) {
				        			
					        		query.append(" INTEGER, FOREIGN KEY(" + name + ") ");
				        			name = name.substring(0, name.length() - 2);
					        		query.append("REFERENCES " + name + "s(id)");
					        		
					        	}else {
					        		
					        		query.append(" INT NOT NULL ");
					        	}		        		
				        		break;	
				        		
				        	case "class java.time.LocalDate":
				        	case "class java.time.LocalDateTime":
				        		query.append(" DATE NOT NULL ");
				        		break;				        	
				        	default:
				        		break;
			        	
			        	}
		        	}
		        	
			        
		        }
		        
	        }
	        
	        	db.executeUpdate(query.toString() + ")");
	        
			}catch(Exception e) {
				throw new SQLException("Cannot create new table");
			}
		
		}
	
	
	@Override
	public boolean add(T element) {// Add new element to the database. Retrieve all the field name and their values from the object and builds the 
		//string query
		
		boolean added = false;
		
		try(Connection conn = db.connect()) {
			
            String insertFields = "";
            String insertValues = "";
            
            
            for(Class<?> c = clazz; c != null; c= c.getSuperclass()) {            	
            
		        for (Field field : c.getDeclaredFields()) {
		        	
		        	field.setAccessible(true);
		            String name = field.getName();               
		
		            insertFields += name + ",";               
		           
		            if(field.getType().equals(String.class) || field.getType().equals(LocalDate.class) || field.getType().equals(LocalDateTime.class)) {
		                                     
		                insertValues += "'" + field.get(element).toString() + "',"; 
		                
		            }else {
		            	
		            	insertValues += field.get(element) + ",";
		            	
		            }
		                
		            
		            
		        }
            }
            
            insertFields = insertFields.substring(0, insertFields.length() - 1);
            insertValues = insertValues.substring(0, insertValues.length() - 1);

            db.executeUpdate("insert into " + tableName + " (" + insertFields + ") values (" + insertValues + ");");
            
            added = true;
            
        } catch (Exception e) {
            added = false;
        }
		return added;
    }
	
	@Override
	public Set<T> find(Predicate<T> func){// Allows to find any element into database filter by any kind of chosen attributes
		//First store all database entities to a Set and then filtering the set based on the function passed as method argument
		
		Set<T> set = findAll();
		set = set.stream().filter(func).collect(Collectors.toSet());
		return set;
		
	}
	
	@Override
	public T findById(int id){//Retrieve an element from database by its id. Operation that can be achieved also with the above method
		
		T found = null;
	
		try(Connection conn = db.connect()) {
			
			ResultSet rs = db.executeQuery("SELECT * FROM " + tableName + " WHERE id=" + id + ";");
			
			while(rs.next()) {
				
				found = (T) clazz.newInstance();
				
				for(Class<?> c = clazz; c != null; c= c.getSuperclass()) {
					
					for(Field field : c.getDeclaredFields()) {
						
						field.setAccessible(true);
						
						if(field.getType().equals(LocalDate.class)) {
							
							field.set(found, LocalDate.parse(rs.getObject(field.getName()).toString()));
							
						}else {
							field.set(found, rs.getObject(field.getName()));
						}
						
					}
				}				
				
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			found = null;
		}
		
		
		//System.out.println(found.toString());
		return found;
	}
	
	
	@Override
	public Set<T> findAll(){// Retrieve all the entities from table
		
		Set<T> rows = new HashSet<>();
		
		try(Connection conn = db.connect()) {
			
			ResultSet rs = db.executeQuery("SELECT * FROM " + tableName + ";"); 
			
			while(rs.next()) {
				
				T found = (T) clazz.newInstance();
				
				for(Class<?> c = clazz; c != null; c= c.getSuperclass()) {
					
					for(Field field : c.getDeclaredFields()) {
						field.setAccessible(true);
						
						if(field.getType().equals(LocalDate.class)) {
							//Set the field of the found variable equal to the field value of the current examined entity
							field.set(found, LocalDate.parse(rs.getObject(field.getName()).toString()));
							
						}else if(field.getType().equals(LocalDateTime.class)) {
							
							field.set(found, LocalDateTime.parse(rs.getObject(field.getName()).toString()));
						}
						else {
							field.set(found, rs.getObject(field.getName()));
						}
						
					}
				}
				
				rows.add(found);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rows;
		
		
	}
	@Override
	public boolean delete(int id) { // Delete entity from database passing the id       
        
        boolean deleted = false;
        
        try(Connection conn = db.connect()) {
        	
        	db.executeUpdate("DELETE FROM " + tableName + " WHERE id=" + id + ";");
            deleted = true;
            
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			deleted = false;
		}
        return deleted;
    }
	
	@Override
	public boolean contains(int id) {// Check if an element is into database
		
		boolean isEmpty = false;
		
		try(Connection conn = db.connect()) {
			
			ResultSet rs = db.executeQuery("SELECT * FROM " + tableName + " WHERE id=" + id + ";");  
			isEmpty = rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			isEmpty = false;
		}
		return isEmpty;
	}
	
	//Update element
	public boolean update(T element) {
		
		boolean updated = false;
		
		try (Connection conn = db.connect()) {
			
            String updateCols = "";          

            for(Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            	
                for (Field field : c.getDeclaredFields()) {  
                	
                	field.setAccessible(true);
                    String name = field.getName();
                   
                    if(field.getType().equals(String.class) || field.getType().equals(LocalDate.class) || field.getType().equals(LocalDateTime.class)) {
                    	
                    	updateCols += name + " = '" + field.get(element).toString() + "',";
                    	
                    }else {
                    	
                    	updateCols += name + " = " + field.get(element) + ",";
                    }
                   
                }
            }
            
           
            Field idField = element.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            db.executeUpdate("UPDATE " + tableName + " SET " + updateCols.substring(0, updateCols.length() - 1) + " WHERE id=" + idField.get(element) + ";");
            updated = true;
        } catch (Exception e) {
            updated = false;
        }
		 
	     return updated;
	     
	}
	@Override
	public void save(T element) {// Save element. Add to the database if object is not into database otherwise update it.
		
		try {
			//System.out.println(find(element.getClass().getField("id"), element.getClass().getField("id").get(element)) == null);
			Field field = element.getClass().getDeclaredField("id");
			field.setAccessible(true);	
			Integer i = (Integer)field.get(element);
			if(field.get(element) == null || !contains(i)) {
				add(element);
			}else {
				update(element);
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalStateException("Cannot be saved!");
		}
		

	}
		
	
}
