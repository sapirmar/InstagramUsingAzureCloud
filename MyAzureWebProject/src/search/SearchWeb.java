package search;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchWeb implements ISearch {

	private Configuration conf;

	public SearchWeb() {
		conf = new Configuration();
	}

	/**
	 * search instagram account on bing
	 * @param searchQuery
	 * @return resualt
	 */
	public SearchResults searchWeb(String searchQuery) {
		try {
			// Look for specific instagram acount
			searchQuery += " instagram account";
			URL url = new URL(conf.getHost() + conf.getPath() + "?q=" + URLEncoder.encode(searchQuery, "UTF-8"));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Ocp-Apim-Subscription-Key", conf.getSubscriptionKey());

			// receive JSON body
			InputStream stream = connection.getInputStream();
			Scanner scanner = new Scanner(stream);
			String response = scanner.useDelimiter("\\A").next();

			// construct result object for return
			SearchResults results = new SearchResults(new HashMap<String, String>(), response);

			// extract Bing-related HTTP headers
			Map<String, List<String>> headers = connection.getHeaderFields();
			for (String header : headers.keySet()) {
				if (header == null)
					continue; // may have null key
				if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
					results.getRelevantHeaders().put(header, headers.get(header).get(0));
				}
			}
			stream.close();
			scanner.close();

			return results;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * pretty-printer for JSON and return the first instagram page. if no
	 * instagram-> return "no account found"
	 * 
	 * @param json_text
	 * @return string
	 */
	public String prettify(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();

		JsonObject obj = json.getAsJsonObject("webPages");
		JsonObject value = obj.getAsJsonArray("value").get(0).getAsJsonObject();
		JsonElement elem = value.get("displayUrl");
		String instagramURL = elem.toString();
		if (instagramURL.contains("https://www.instagram.com/")) {
			// return the first instagram page
			return instagramURL;
		} else {
			return "no account found";
		}

	}

	public String search(String searchQuery) {
		SearchResults results = searchWeb(searchQuery);
		String jsonResult = prettify(results.getJsonResponse());
		return jsonResult;
	}

}
