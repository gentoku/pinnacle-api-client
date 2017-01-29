package pinnacle.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class ParameterCore {

	/**
	 * Parameters as Map.
	 */
	protected Map<String, Object> parameters;

	/**
	 * Getter
	 * 
	 * @return
	 */
	Map<String, Object> parameters() {
		return this.parameters;
	}

	protected boolean hasNull (Object... varargs) {
		for (Object arg : varargs) {
			if (arg == null) return true;
		}
		return false;
	}
	
	
	/**
	 * List of sub-parameters. Required as a nested array.
	 */
	protected List<Parameter> legs;

	/**
	 * Returns legs. Use this method only for a parameter which must have
	 * 'legs', which means you confirm that legs must be set so this throws
	 * exception when legs is null.
	 * 
	 * @return
	 * @throws PinnacleException
	 */
	List<Parameter> legs() throws PinnacleException {
		if (this.legs == null) {
			throw PinnacleException.parameterInvalid("'legs' cannot be/contain null.");
		}
		return this.legs;
	}

	/**
	 * List of sub-parameters. Required as a nested array.
	 */
	protected List<Parameter> bets;

	/**
	 * Returns bets. Use this method only for a parameter which must have
	 * 'bets', which means you confirm that bets must be set so this throws
	 * exception when bets is null.
	 * 
	 * @return
	 * @throws PinnacleException
	 */
	List<Parameter> bets() throws PinnacleException {
		if (this.bets == null){
			throw PinnacleException.parameterInvalid("'bets' cannot be/contain null.");
		}
		return this.bets;
	}

	/**
	 * True when parameter got 'since'. For fair use.
	 */
	protected boolean withSince = false;

	boolean withSince() {
		return this.withSince;
	}

	/**
	 * Constructor
	 */
	protected ParameterCore() {
		this.parameters = new ConcurrentHashMap<>(); // to lower-case key names
														// by stream
	}
	
	/**
	 * Unites key and value with '=' and joins them with '&' for GET method
	 * request.
	 * 
	 * @return
	 * @throws PinnacleException
	 */
	String toUrlQuery() {
		return this.parameters.entrySet().stream().filter(p -> p != null)
				.map(p -> p.getKey() + "=" + p.getValue()).collect(Collectors.joining("&"));
	}

	/**
	 * Converts parameters to Json to request by POST method. legs or bets
	 * converted into nested array if not null. Null check is NOT to validate
	 * but to confirm whether or not the parameter requires it. They should 
	 * have already validated by their getters which should be called when they
	 * are required.
	 * 
	 * @return
	 */
	String toJson() {
		if (this.legs != null)
			this.parameters.put("legs", this.getSubParametersAsList(this.legs));
		if (this.bets != null)
			this.parameters.put("bets", this.getSubParametersAsList(this.bets));
		Gson gson = new Gson();
		return gson.toJson(this.parameters);
	}

	/**
	 * Returns sub parameters as list of map.
	 * 
	 * @param subParameters
	 */
	private List<Map<String, Object>> getSubParametersAsList(List<Parameter> subParameters) {
		return subParameters.parallelStream().map(subParameter -> subParameter.parameters).collect(Collectors.toList());
	}

	/**
	 * URL-encodes a text. Converts UnsupportedEncodingException to
	 * PinnacleException.
	 * 
	 * @param text
	 * @return
	 * @throws PinnacleException
	 */
	protected String urlEncode(String text) throws PinnacleException {
		try {
			return URLEncoder.encode(text, Constants.CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw PinnacleException
					.parameterInvalid("URL encoding failed. UnsupportedEncodingException: " + e.getMessage());
		}
	}

}
