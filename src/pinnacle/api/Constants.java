package pinnacle.api;

import java.time.ZoneId;

public class Constants {

	/**
	 * Base URL for Pinnacle API.
	 */
	static final String BASE_URL = "https://api.pinnacle.com";

	/**
	 * Time zone used by Pinnacle API. All dates and times are in UTC time zone,
	 * ISO 8601 format
	 */
	static final ZoneId TIME_ZONE = ZoneId.of("UTC");

	/**
	 * Character set
	 */
	static final String CHARSET = "UTF-8";

	/**
	 * Fair use interval between requests. Requests made for the /fixtures and
	 * /odds operation with the since parameter must be restricted to once every
	 * 5 seconds. (Recommended)
	 */
	static final int INTERVAL_WITH_SINCE = 5000; // 5000 milliseconds = 5
													// seconds

	/**
	 * Fair use interval between requests. Requests made for the /fixtures and
	 * /odds operation without the since parameter must be restricted to once
	 * every 60 seconds;
	 */
	static final int INTERVAL_WITHOUT_SINCE = 60000; // 60000 milliseconds = 60
														// seconds

	/**
	 * Voluntary interval as a manner.
	 */
	static final int VOLUNTARY_INTERVAL = 1000;

	/**
	 * Connection will be closed if no response within this value.
	 */
	static final int CONNECTION_TIMEOUT = 10000; // 10000 milliseconds = 10
													// seconds

	/**
	 * Request will retry up to this number when receiving '5xx Server Error'.
	 */
	static final int MAX_RETRY = 1;

}
