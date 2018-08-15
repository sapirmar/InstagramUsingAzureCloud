package search;


import java.util.Map;

public class SearchResults {

	private Map<String, String> relevantHeaders;
    private String jsonResponse;
    
    SearchResults(Map<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }

	public Map<String, String> getRelevantHeaders() {
		return relevantHeaders;
	}

	public void setRelevantHeaders(Map<String, String> relevantHeaders) {
		this.relevantHeaders = relevantHeaders;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
    
    
}
