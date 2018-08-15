package azureStorage;

import com.microsoft.azure.storage.table.TableServiceEntity;
/**
 * Representing a person in the table azure.
 * @author User
 *
 */
public class PersonEntity extends TableServiceEntity {

	
	private String link;
	
	public PersonEntity() {
		
	}

	public PersonEntity(String name, String link) {
		super();
		this.rowKey = name;
		this.link=link;
		this.partitionKey = "instagramAccount" ;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	

}
