package azureStorage;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;
/**
 * Access to TableAzure database.
 * Singleton design pattern.
 * @author User
 *
 */
public class StorageAzure implements IStorage<PersonEntity> {

	private String connectionString; 
	private CloudTableClient tableClient;
	private CloudTable cloudTable;
	private final String ROW_KEY = "RowKey";
	private CloudStorageAccount storageAccount;
	
	private static StorageAzure storage = null;

     //method to return instance of class
	public static StorageAzure getInstance() {

		if (storage == null) {
			synchronized (StorageAzure.class) {
				if (storage == null) {
					storage = new StorageAzure();
				}
			}

		}
		return storage;
	}

	// private constructor
	private StorageAzure() {

		connectionString = "***";
		connectToDB();

	}

	/**
	 * connect to database and create the table.
	 */
	public void connectToDB() {

		try {
			storageAccount = CloudStorageAccount.parse(connectionString);
			tableClient = storageAccount.createCloudTableClient();

			// Create the table if it doesn't exist.
			String tableName = "names";
			cloudTable = tableClient.getTableReference(tableName);
			cloudTable.createIfNotExists();

		} catch (InvalidKeyException | URISyntaxException | StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * write PersonEntity to TableAzure.
	 */
	@Override
	public PersonEntity write(PersonEntity person) {

		// Create an operation to add the new Person to the "names" table.
		TableOperation insertPerson = TableOperation.insertOrReplace(person);

		try {
			// Submit the operation to the table service.
			cloudTable.execute(insertPerson);

		} catch (StorageException e) {
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}
		return person;

	}
/**
 * read from TableAzure.
 * Sort the list in descending order and return the list.
 */
	@Override
	public List<PersonEntity> read() {

		List<PersonEntity> list = new LinkedList<>();
		TableQuery<PersonEntity> partitionQuery = TableQuery.from(PersonEntity.class);

		list = sortDescending(list);
		for (PersonEntity entity : cloudTable.execute(partitionQuery)) {
			list.add(entity);
			System.out.println(entity.getRowKey() + " " + entity.getLink() + " " + entity.getTimestamp());

		}
		return sortDescending(list);
	}

	/**
	 * Sort the list in descending order, according to the "Timestamp".
	 * @param list
	 * @return
	 */
	public List<PersonEntity> sortDescending(List<PersonEntity> list) {
		list.sort(new Comparator<PersonEntity>() {

			@Override
			public int compare(PersonEntity p1, PersonEntity p2) {
				// TODO Auto-generated method stub
				return p2.getTimestamp().compareTo(p1.getTimestamp());
			}
		});
		return list;

	}

	/**
	 * check if the name exists. if exists return the link, else return null.
	 * 
	 * @param rowKey
	 * @return the link if exists.
	 */
	public String checkIfExists(String rowKey) {
		String link = null;

		String partitionFilter = TableQuery.generateFilterCondition(ROW_KEY, QueryComparisons.EQUAL,
				rowKey.toUpperCase());

		// Specify a partition query, using "Smith" as the partition key filter.
		TableQuery<PersonEntity> partitionQuery = TableQuery.from(PersonEntity.class).where(partitionFilter);
		for (PersonEntity entity : cloudTable.execute(partitionQuery)) { // there is just one result
			link = entity.getLink();
			break;
		}
		if (link != null) {
			updateTimeEntity(new PersonEntity(rowKey.toUpperCase(), link));
		}
		return link;

	}
/**
 * update the "timeset" of PersonEntity.
 * @param p
 */
	public void updateTimeEntity(PersonEntity p) {
		p.setTimestamp(new Date());

		TableOperation updatePerson = TableOperation.insertOrReplace(p);
		try {
			cloudTable.execute(updatePerson);
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
