package pinnacle.api;

import java.io.IOException;

/**
 * A Exception thrown by this package.
 */
@SuppressWarnings("serial")
public class PinnacleException extends IOException {

	enum TYPE {
		CONNECTION_FAILED, ERROR_RETURNED, PARAMETER_INVALID, JSON_UNPARSABLE;
	}

	private TYPE errorType;

	public TYPE errorType() {
		return this.errorType;
	}

	private String errorJson;

	public String errorJson() {
		return this.errorJson;
	}

	/*
	 * public GenericException parsedErrorJson () throws PinnacleException {
	 * return GenericException.parse(this.errorJson); }
	 */
	private int statusCode;

	public int statusCode() {
		return this.statusCode;
	}

	private String unparsableJson;

	public String unparsableJson() {
		return this.unparsableJson;
	}

	private PinnacleException(String message, TYPE errorType) {
		super(message);
		this.errorType = errorType;
	}

	static PinnacleException connectionFailed(String message) {
		return new PinnacleException(message, TYPE.CONNECTION_FAILED);
	}

	static PinnacleException errorReturned(int statusCode, String errorJson) {
		PinnacleException ex = new PinnacleException("API returned an error response:[" + statusCode + "] " + errorJson,
				TYPE.ERROR_RETURNED);
		ex.statusCode = statusCode;
		ex.errorJson = errorJson;
		return ex;
	}

	static PinnacleException parameterInvalid(String message) {
		return new PinnacleException(message, TYPE.PARAMETER_INVALID);
	}

	static PinnacleException jsonUnparsable(String unparsableJson) {
		PinnacleException ex = new PinnacleException("Couldn't parse JSON: " + unparsableJson, TYPE.JSON_UNPARSABLE);
		ex.unparsableJson = unparsableJson;
		return ex;
	}
}
