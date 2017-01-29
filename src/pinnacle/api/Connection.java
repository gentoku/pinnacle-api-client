package pinnacle.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class Connection {

	/**
	 * Milliseconds For both connect timeout and read timeout.
	 */
	private static final int TIMEOUT = 10000;
	
	/**
	 * Request will retry up to this number when receiving '5xx Server Error'.
	 */
	private static final int MAX_RETRY = 0;
	// TODO
	
	/**
	 * Encoded username and password.
	 */
	private String credentials;
	
	/**
	 * Constructor is private.
	 * @param encodedId
	 */
	private Connection (String credentials) {
		this.credentials = credentials;
	}
	
	/**
	 * Factory needs encoded ID.
	 * @param encodedId
	 * @return
	 */
	static Connection newInstance (String credentials) {
		return new Connection(credentials);
	}
	
	/**
	 * Sets common headers to the request and gets a response from it.
	 * @param request
	 * @return
	 * @throws IOException
	 */
	Response execute (Request request) throws IOException {
		request.header("Accept-Charset", PinnacleAPI.CHARSET);
		request.header("Accept-Encoding", "gzip"); // API supports and recommends HTTP Compression. 
		request.header("Authorization", "Basic " + this.credentials);
		return Response.execute(request);
	}
	
	static class Request {
		
		/**
		 * Target URL.
		 */
		private URL url;
		
		URL url () {
			return this.url;
		}
		
		/**
		 * Request Method must be GET or POST.
		 */
		private String method = "GET";

		String method () { 
			return this.method;
		}
		
		Request get () {
			this.method = "GET";
			return this;
		}
		
		Request post (Parameter parameters) {
			this.query = parameters.json();
			this.method = "POST";
			return this;
		}
		
		/**
		 * Format must be JSON or XML. Note that long term aim is to support 
		 * just the JSON format as a more compact format than XML.
		 */
		private String format = "json";
		
		String format () {
			return this.format;
		}
		
		Request xml () {
			this.format = "xml";
			return this;
		}

		Request json () {
			this.format = "json";
			return this;
		}

		/**
		 * Sets format into header. This may be done twice as accepting random order
		 * to set GET/POST and JSON/XML. 
		 * Fixed JSON format for request, XML format can be set as well though. 
		 */
		private void setFormat () {
			this.header.put("Accept", "application/" + this.format); // response format
			if (this.method.equals("POST")) {
				this.header.put("Content-Type", "application/json"); // request format
			}
		}
		
		/**
		 * query.
		 */
		private String query;

		String query () {
			return this.query;
		}
		
		/**
		 * Requeset header.
		 */
		private Map<String, String> header;
		
		Map<String, String> header () {
			return this.header;
		};
		
		Request header (Map<String, String> header) { 
			this.header = header;
			return this;
		}
		
		Request header (String key, String value) { 
			this.header.put(key, value);
			return this;
		}

		private int throttle = PinnacleAPI.THROTTLE;
		
		int throttle () {
			return this.throttle;
		}

		Request throttle (int throttle) {
			this.throttle = throttle;
			return this;
		} 

		/**
		 * Constructor and Factory.
		 */
		Request (URL url) {
			this.url = url;
			this.header = new HashMap<>();
		}
		
		static Request newInstance (URL url) {
			return new Request(url);
		}
	}
	
	static class Response {

		/**
		 * Response data as plain text.
		 */
		private String text;
		
		String text () { 
			return this.text;
		}

		
		/**
		 * Status code.
		 */
		private int status;
		
		int status () {
			return this.status;
		}
		
		private int retry;
		
		/**
		 * Constructor counts how many times this has tried to access.
		 * @param previous
		 */
		private Response (Response previous) {
			if (previous == null) {
				this.retry = 0;
			} else {
				this.retry = previous.retry + 1;
			}
		}
		
		/**
		 * First time execution.
		 * @param request
		 * @return
		 * @throws IOException
		 */
		static Response execute (Request request) throws IOException { 
			return execute(request, null);
		}
		
		/**
		 * Executes the request and gets response. Retries for 5xx server error.
		 * @param request
		 * @param previousResponse
		 * @return
		 * @throws IOException
		 */
		private static Response execute (Request request, Response previousResponse) throws IOException {
			request.setFormat();
			if (request.throttle() > 0) sleep(request.throttle());
			HttpURLConnection connection = openConnection(request);
			Response response = new Response(previousResponse);
			response.status = connection.getResponseCode();
			if (response.status >= 500 && response.retry < MAX_RETRY) {
				sleep(TIMEOUT * response.retry);
				execute(request, response);
			}
			if (response.status >= 400) { // error
				response.text = read(connection.getErrorStream());
				String errorMessage = "[HTTP Status Code: " + response.status + "]"; 
				String jsonErrorMessage = Json.parseErrorMessage(response.text);
				if (jsonErrorMessage != null) throw new PinnacleException(errorMessage + jsonErrorMessage);
				String xmlErrorMessage = Xml.parseErrorMessage(response.text);
				if (xmlErrorMessage != null) throw new PinnacleException(errorMessage + xmlErrorMessage);
				throw new PinnacleException(errorMessage + response.text);
			} else {
				response.text = read(getInputStream(connection)); 
			}
			return response;
		}

		private static void sleep (int milliseconds) {
			try {
				Thread.sleep(milliseconds);
			} catch (InterruptedException e) {
				// ignore. 
			}
		}
		
		/**
		 * Opens connection to API with request. 
		 * @param request
		 * @return
		 * @throws IOException
		 */
		private static HttpURLConnection openConnection (Request request) throws IOException {
			PinnacleAPI.access(request.url.toString());
			HttpURLConnection connection = (HttpURLConnection) request.url().openConnection();
			connection.setRequestMethod(request.method());
			connection.setInstanceFollowRedirects(false); // don't rely on native redirection support
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
			request.header().entrySet()
				.forEach(header -> connection.setRequestProperty(header.getKey(), header.getValue()));
			if (request.method().equals("POST")) {
				connection.setDoOutput(true);
				try (OutputStream output = connection.getOutputStream()) {
					output.write(request.query().getBytes(PinnacleAPI.CHARSET));
				}
			}
			return connection;
		}
		
		/**
		 * Gets plain text from inputstream.
		 * @param connection
		 * @return
		 * @throws IOException
		 */
		private static String read (InputStream is) throws IOException {
			StringBuilder convertedString = new StringBuilder();
			String newLine = System.getProperty("line.separator");
			String line = "";
			try (final BufferedReader reader = 
					new BufferedReader(
							new InputStreamReader(is))) {
				while ((line = reader.readLine()) != null) {
				    convertedString.append(line);
				    convertedString.append(newLine);
				}
			}
			return convertedString.toString();
		} 
		
		private static InputStream getInputStream (HttpURLConnection connection) throws IOException {
			return isGzipped(connection) 
					? new GZIPInputStream(connection.getInputStream())
					: connection.getInputStream();
		}
		
		private static boolean isGzipped (HttpURLConnection connection) {
			return connection.getHeaderField("Content-Encoding") == null
					? false
					: connection.getHeaderField("Content-Encoding").equals("gzip");
		}
	}
}
