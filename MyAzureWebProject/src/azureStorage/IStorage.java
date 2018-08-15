package azureStorage;

import java.util.List;

public interface IStorage<T> {
	public T  write(T t);
	public  List <T>read();
	

}
