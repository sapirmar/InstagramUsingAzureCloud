package search;

public class Configuration {
	 private String subscriptionKey;
	    private String host;
	    private String path;

	    private String searchTerm;
	    
	    public Configuration() {
	    	this.subscriptionKey="***";
	    	this.host="https://api.cognitive.microsoft.com";
	    	this.path= "/bing/v5.0/search";
	    	this.searchTerm= "Microsoft Cognitive Services";
	    }

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getSearchTerm() {
			return searchTerm;
		}

		public void setSearchTerm(String searchTerm) {
			this.searchTerm = searchTerm;
		}

		public String getSubscriptionKey() {
			return subscriptionKey;
		}

		public void setSubscriptionKey(String subscriptionKey) {
			this.subscriptionKey = subscriptionKey;
		}
	    
	    

}
