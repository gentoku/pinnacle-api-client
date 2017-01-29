package pinnacle.api;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Request {

	/**
	 * Target URL.
	 */
	private URL url;

	URL url() {
		return this.url;
	}

	/**
	 * HTTP connection method. GET or POST for this API.
	 */
	private METHOD method;

	METHOD method() {
		return this.method;
	}

	enum METHOD {
		GET, POST;
	}

	/**
	 * Interval milliseconds between requests. Default is set 1000ms = 1s as a
	 * manner. Some operations require 'fair use' interval more than 1 sec.
	 */
	private int interval = Constants.VOLUNTARY_INTERVAL;

	int interval() {
		return this.interval;
	}

	/**
	 * Request header.
	 */
	private Map<String, String> header;

	Map<String, String> header() {
		return this.header;
	}

	/**
	 * Parameter.
	 */
	private Parameter parameter;

	Parameter parameter() {
		return this.parameter;
	}

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	private Request(URL url) {
		this.url = url;
		this.header = new HashMap<>();
		this.header.put("Accept-Charset", Constants.CHARSET);
		this.header.put("Accept-Encoding", "gzip"); // API supports and recommends HTTP Compression.
		this.header.put("Accept", "application/json"); // response format
	}

	/**
	 * Factory
	 * 
	 * @param url
	 * @return
	 */
	static Request newRequest(URL url) {
		return new Request(url);
	}

	/**
	 * Executes the request and returns its response. Needs credentials.
	 * 
	 * @param credentials
	 * @return
	 * @throws PinnacleException
	 */
	Response execute(String credentials) throws PinnacleException {
		try {
			this.header.put("Authorization", "Basic " + credentials);
			return Response.respondTo(this);
		} catch (IOException e) {
			throw PinnacleException.connectionFailed(e.getMessage());
		}
	}

	/**
	 * Sets HTTP method as GET.
	 * 
	 * @return
	 */
	Request get() {
		this.method = METHOD.GET;
		return this;
	}

	/**
	 * Sets HTTP method as POST.
	 * 
	 * @param parameter
	 * @return
	 */
	Request post(Parameter parameter) {
		this.method = METHOD.POST;
		this.parameter = parameter;
		this.header.put("Content-Type", "application/json"); // request format
		return this;
	}

	/**
	 * Toggles fair use interval.
	 * 
	 * @return
	 */
	Request fairUse(boolean withSince) {
		this.interval = this.fairUseInterval(withSince);
		return this;
	}

	/**
	 * Returns required interval between requests by Pinnacle. Depending on
	 * with/without 'since' parameter.
	 * 
	 * @return
	 */
	int fairUseInterval(boolean withSince) {
		return withSince ? Constants.INTERVAL_WITH_SINCE : Constants.INTERVAL_WITHOUT_SINCE;
	}

}
