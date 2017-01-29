package pinnacle.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Validator {

	/**
	 * Parameters as Map.
	 */
	private Map<String, Object> parameters;

	/**
	 * Keys that the parameter CAN have.
	 */
	private Set<String> validKeys;

	/**
	 * Constructor takes parameters as Map from Parameter.
	 * 
	 * @param parameter
	 */
	Validator(Parameter parameter) {
		this.parameters = parameter.parameters();
		this.validKeys = new HashSet<>();
	}

	/**
	 * Returns the value of parameter as Object.
	 * 
	 * @param key
	 * @return
	 * @throws PinnacleException
	 */
	Object getValue(String key) throws PinnacleException {
		if (!this.hasKeyWithValue(key))
			throw PinnacleException.parameterInvalid(key + " not found in parameters or has no value.");
		return this.parameters.get(key);
	}

	/**
	 * True when this.parameter has the key with a solid value.
	 * 
	 * @param key
	 * @return
	 * @throws PinnacleException
	 */
	private boolean hasKeyWithValue(String key) {
		return this.parameters.containsKey(key) && this.parameters.get(key) != null;
	}

	/**
	 * Adds a required key. Parameters MUST have the key with a value. No need
	 * to validate type of value. It should be set properly by methods in
	 * Parameter class.
	 * 
	 * @param key
	 * @throws PinnacleException
	 */
	void addRequiredKey(String key) throws PinnacleException {
		if (!this.hasKeyWithValue(key))
			throw PinnacleException.parameterInvalid(key + " must be set in parameters.");
		this.validKeys.add(key);
	}

	/**
	 * Adds an optional key. Parameters CAN have the key.
	 * 
	 * @param key
	 */
	void addOptionalKey(String key) {
		this.validKeys.add(key);
	}

	/**
	 * Validates keys. Throws IllegalArgumentException when parameters contain
	 * any key other than keys defined as this.validKeys.
	 * 
	 * @throws PinnacleException
	 */
	void validateKeys() throws PinnacleException {
		for (String key : this.parameters.keySet()) {
			this.validateKey(key);
		}
	}

	/**
	 * Checks if the key is valid to be set. Throws IllegalArgumentException if
	 * not a valid key for request.
	 * 
	 * @param key
	 * @throws PinnacleException
	 */
	private void validateKey(String key) throws PinnacleException {
		if (!this.validKeys.contains(key))
			throw PinnacleException.parameterInvalid(key + " is an invalid parameter.");
	}
}
