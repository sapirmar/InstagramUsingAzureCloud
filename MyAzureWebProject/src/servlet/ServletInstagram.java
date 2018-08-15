package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import azureStorage.PersonEntity;
import azureStorage.StorageAzure;
import search.ISearch;
import search.SearchWeb;

public class ServletInstagram extends HttpServlet {

	private static final long serialVersionUID = 2530608420225369078L;


	public ServletInstagram() {

	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		StorageAzure sa = StorageAzure.getInstance();
		ISearch search = new SearchWeb();
		String name = request.getParameter("name");

		//check if the person already exists in the table
		//If exists-> return the link from the table;
		String link = sa.checkIfExists(name);
		
		if (link == null)// the person not exists
		{
			//Search the link on Bing
			link = search.search(name);
			
			if(link.compareTo("no account found")!=0) {
			// Create a new person entity.
			PersonEntity person = new PersonEntity(name.toUpperCase(), link);// row key=name , partition key =
																				// link
			sa.write(person);
			}

		}

		response.setContentType("text/html");
		request.setAttribute("res", link);
		request.getRequestDispatcher("index.jsp").forward(request, response);

	}
}
