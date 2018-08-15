package azureStorage;

import java.util.List;

import search.ISearch;
import search.SearchWeb;

public class Run {

	public static void main(String[] args) {
		StorageAzure sa = StorageAzure.getInstance();

		ISearch search = new SearchWeb();
		String name = "higuain";
		
//		String link = sa.checkIfExists(name);
//		if (link == null)// the person not exists
//		{
//			link = search.search(name);
//			// Create a new person entity.
//			PersonEntity person = new PersonEntity(name.toUpperCase(), link);// row key=name , partition key =
//																				// link
//			sa.write(person);
//
//		}
		
		String link=search.search(name);

		PersonEntity p=new PersonEntity(name, link);
		System.out.println(p.getEtag());
		System.out.println(p.getPartitionKey());
		System.out.println(p.getRowKey());
		System.out.println(p.getTimestamp());
		System.out.println(p.getClass());
		sa.write(p);
		
	}

}
