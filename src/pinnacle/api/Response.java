package pinnacle.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import pinnacle.api.Request.METHOD;

public class Response {

	/**
	 * Response data as plain text
	 */
	private String text;

	String text() {
		return this.text;
	}

	/**
	 * Status code
	 */
	private Integer statusCode;

	Integer statusCode() {
		return this.statusCode;
	}

	/**
	 * Attempted URL and its time to access for logging.
	 */
	private Map<Instant, URL> attempts;

	Map<Instant, URL> attempts() {
		return this.attempts;
	}

	/**
	 * Count of retry.
	 */
	private int retry;

	/**
	 * HTTP connection
	 */
	private HttpURLConnection connection;

	/**
	 * Private constructor counts how many times this class is retrying to
	 * access.
	 * 
	 * @param previous
	 */
	private Response(Response previous) {
		if (previous == null) {
			this.retry = 0;
			this.attempts = new HashMap<>();
		} else {
			this.retry = previous.retry + 1;
		}
	}

	/**
	 * First time execution.
	 * 
	 * @param request
	 * @return
	 * @throws PinnacleException
	 */
	static Response respondTo(Request request) throws IOException {
		return respondTo(request, null);
	}

	/**
	 * Executes the request and gets response. Recursive to retry up to
	 * MAX_RETRY times when response code gets 5xx server error. Sleep interval
	 * will be longer after second trial.
	 * 
	 * @param request
	 * @param previousResponse
	 * @return
	 * @throws IOException
	 */
	private static Response respondTo(Request request, Response previousResponse)
			throws IOException, PinnacleException {
		Response response = new Response(previousResponse);
		response.openConnection(request);
		if (response.statusCode >= 500 && response.retry < Constants.MAX_RETRY) { 
			sleep(Constants.CONNECTION_TIMEOUT * response.retry); // no specific meaning but waiting for longer 
			respondTo(request, response); // recursive rerty
		}
		if (response.statusCode >= 400) { // got 4xx error or have retried but still 5xx
			throw PinnacleException.errorReturned(response.statusCode, read(response.connection.getErrorStream()));
		}
		response.text = read(response.connection.getInputStream());
		return response;
	}

	/**
	 * Opens connection to API with request.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private void openConnection(Request request) throws IOException {
		this.attempts.put(Instant.now(), request.url());
		sleep(request.interval());
		this.connection = (HttpURLConnection) request.url().openConnection();
		this.connection.setRequestMethod(request.method().name());
		this.connection.setInstanceFollowRedirects(false); // don't rely on native redirection support
		this.connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
		this.connection.setReadTimeout(Constants.CONNECTION_TIMEOUT);
		request.header().entrySet()
				.forEach(header -> this.connection.setRequestProperty(header.getKey(), header.getValue()));
		if (request.method() == METHOD.POST) {
			this.connection.setDoOutput(true);
			try (OutputStream output = this.connection.getOutputStream()) {
				output.write(request.parameter().toJson().getBytes(Constants.CHARSET));
			}
		}
		this.statusCode = this.connection.getResponseCode();
	}

	/**
	 * Gets InputStream from URLConnection and convert it into plain text.
	 * 
	 * @throws IOException
	 */
	private static String read(InputStream is) throws IOException {
		return readInputStream(new GZIPInputStream(is));
	}

	/**
	 * Gets plain text from InputStream.
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	private static String readInputStream(InputStream is) throws IOException {
		StringBuilder convertedString = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String line = "";
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			while ((line = reader.readLine()) != null) {
				convertedString.append(line);
				convertedString.append(newLine);
			}
		}
		return convertedString.toString();
	}

	/**
	 * Sleeps for the time. Ignores an exception.
	 * 
	 * @param milliseconds
	 */
	private static void sleep(int milliseconds) {
		try {
			System.out.printf("Waiting for %d seconds...%n", milliseconds / 1000);
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// ignore.
		}
	}
}
