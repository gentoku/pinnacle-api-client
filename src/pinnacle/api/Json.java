package pinnacle.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Json {

	private JsonObject json;

	/**
	 * Empty constructor.
	 */
	private Json () {}
	
	/**
	 * Private constructor with text.
	 * @param text
	 * @throws PinnacleException - if verified as a error. 
	 */
	private Json (String text) throws PinnacleException {
		try {
			this.json = new Gson().fromJson(text, JsonObject.class);
		} catch (JsonSyntaxException e) {
			throw new PinnacleException("Malformed JSON.");
		}
	}
	
	/**
	 * Private constructor with JsonObject to use recursively.
	 * The JsonObject must be already verified. 
	 * @param object
	 */
	private Json (JsonElement element) {
		this.json = element.getAsJsonObject();
	}
	
	/**
	 * Factory methods.
	 * @param text
	 * @return
	 * @throws PinnacleException 
	 */
	static Json of (String text) throws PinnacleException {
		return text.equals("") ? new Json() : new Json(text).verify();
	}
	
	static Json of (JsonElement element) {
		return new Json(element);
	}
	
	/**
	 * Tries to parse error message in JSON. Returns null if it is not error. 
	 * @param text
	 * @return
	 */
	static String parseErrorMessage (String text) {
		try {
			Json json = new Json(text);
			return json.getErrorMessage();
		} catch (PinnacleException e) { // malformed JSON
			return null;
		}
	}
	
	/**
	 * Returns error message if the JSON represents error.
	 * Or null if not. 
	 * @return
	 */
	private String getErrorMessage () {
		JsonElement code = this.json.get("code");
		JsonElement message = this.json.get("message");
		if (this.json.get("code") != null && this.json.get("message") != null) {
			return String.format("[Error Code: %s] %s", code.getAsString(), message.getAsString());
		} else {
			return null;
		}
	}
	
	/**
	 * Verifies if the JSON represents error. 
	 * @throws PinnacleException
	 */
	private Json verify () throws PinnacleException {
		String error = this.getErrorMessage();
		if (error != null) throw new PinnacleException(error);
		return this;
	}
	
	/**
	 * Short cuts for 'getAs(Type)'.
	 * @param key
	 * @return
	 */
	Optional<BigDecimal> getAsBigDecimal (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsBigDecimal);
	}
	Optional<BigInteger> getAsBigInteger (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsBigInteger);
	}
	Optional<Boolean> getAsBoolean (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsBoolean);
	}
	Optional<Byte> getAsByte (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsByte);
	}
	Optional<Character> getAsCharacter (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsCharacter);
	}
	Optional<Double> getAsDouble (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsDouble);
	}
	Optional<Float> getAsFloat (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsFloat);
	}
	Optional<Integer> getAsInteger (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsInt);
	}
	Optional<Long> getAsLong (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsLong);
	}
	Optional<Number> getAsNumber (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsNumber);
	}
	Optional<Short> getAsShort (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsShort);
	}
	Optional<String> getAsString (String key) { 
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(JsonElement::getAsString)
				.map(String::trim);
	}

	/**
	 * Returns as Json.
	 * @param key
	 * @return
	 */
	Optional<Json> getAsJson (String key) {
		JsonElement element = this.getElement(key);
		return Optional.ofNullable(element)
				.map(Json::of);
	}

	/**
	 * Returns <code>JsonElement</code> of the key or null. 
	 * @param key
	 * @return
	 */
	private JsonElement getElement (String key) {
		if (this.json == null) return null; 
		JsonElement element = this.json.get(key);
		if (element == null) return null; // following 'element.isJsonNull()' requires non-null
		return element.isJsonNull()	? null : element;
	}

	/**
	 * Returns stream of Strings if the key has an array of string. 
	 * Or else returns an empty stream.
	 * @param key
	 * @return
	 */
	Stream<String> getAsStringStream (String key) {
		return this.getAsStream(key).map(JsonElement::getAsString);
	}
	
	/**
	 * Returns stream of Jsons if the key has an array of JSON objects. 
	 * Or else returns an empty stream.
	 * @param key
	 * @return
	 */
	Stream<Json> getAsJsonStream (String key) {
		return this.getAsStream(key).map(Json::of);
	}
	
	// No other getAsXXXStream is not necessary for pinnacle api response currently. 
	
	/**
	 * Returns stream of JsonElements if the key has an array of JSON objects.
	 * Or else returns an empty stream.
	 * @param key
	 * @return
	 */
	private Stream<JsonElement> getAsStream (String key) {
		JsonElement element = this.getElement(key);
		if (element == null || !element.isJsonArray()) return Stream.empty();
		return this.toStream(element);
	}
	
	/**
	 * Converts an array of JsonElements to stream of JsonElements.
	 * @param element has been already validated as a JsonArray.
	 * @return
	 */
	private Stream<JsonElement> toStream (JsonElement element) {
		JsonArray array = element.getAsJsonArray();
		Spliterator<JsonElement> spliterator = 
				Spliterators.spliterator(array.iterator(), array.size(), 64);
		return StreamSupport.stream(spliterator, true); // true for parallel
	}
	
	// test
	public static void main(String[] args) throws PinnacleException {
		String text = "{\"code\":\"UNAUTHORIZED\",\"message\":\"Invalid credentials\"}";
		Json.of(text); // throws OfficialThrownException
	}
}
