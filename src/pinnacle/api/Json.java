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

	private JsonObject jsonObject;

	/**
	 * Private constructor by JsonObject.
	 * 
	 * @param json
	 */
	private Json(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * Factory
	 * 
	 * @param text
	 * @return
	 * @throws PinnacleException
	 */
	public static Json of(String text) throws PinnacleException {
		try {
			JsonObject jsonObject = new Gson().fromJson(text, JsonObject.class);
			Json json = new Json(jsonObject);
			if (json.isErrorJson())
				throw PinnacleException.errorReturned(200, text);
			return json;
		} catch (JsonSyntaxException e) {
			throw PinnacleException.jsonUnparsable("JSON syntax error: " + text);
		}
	}

	private boolean isErrorJson() {
		return this.getAsString("code").isPresent() && this.getAsString("message").isPresent();
	}

	private Optional<JsonElement> getElement(String key) {
		return Optional.ofNullable(this.jsonObject.get(key));
	}

	public Optional<BigDecimal> getAsBigDecimal(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsBigDecimal);
	}

	Optional<BigInteger> getAsBigInteger(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsBigInteger);
	}

	public Optional<Boolean> getAsBoolean(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsBoolean);
	}

	/*
	 * Optional<Byte> getAsByte (String key) { return
	 * this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement
	 * ::getAsByte); }
	 * 
	 * Optional<Character> getAsCharacter (String key) { return
	 * this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement
	 * ::getAsCharacter); }
	 * 
	 * Optional<Double> getAsDouble (String key) { return
	 * this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement
	 * ::getAsDouble); }
	 * 
	 * Optional<Float> getAsFloat (String key) { return
	 * this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement
	 * ::getAsFloat); }
	 */

	public Optional<Integer> getAsInteger(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsInt);
	}

	public Optional<Long> getAsLong(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsLong);
	}

	/*
	 * Optional<Number> getAsNumber (String key) { return
	 * this.getElement(key).map(JsonElement::getAsNumber); }
	 * 
	 * Optional<Short> getAsShort (String key) { return
	 * this.getElement(key).map(JsonElement::getAsShort); }
	 */

	public Optional<String> getAsString(String key) {
		return this.getElement(key).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsString);
	}

	public Optional<Json> getAsJson(String key) {
		return this.getElement(key).filter(JsonElement::isJsonObject).map(JsonElement::getAsJsonObject).map(Json::new);
	}

	public Stream<String> getAsStringStream(String keyOfArray) {
		return this.getAsStream(keyOfArray).filter(JsonElement::isJsonPrimitive).map(JsonElement::getAsString);
	}

	public Stream<Json> getAsJsonStream(String keyOfArray) {
		return this.getAsStream(keyOfArray).filter(JsonElement::isJsonObject).map(JsonElement::getAsJsonObject)
				.map(Json::new);
	}

	private Stream<JsonElement> getAsStream(String keyOfArray) {
		return this.getElement(keyOfArray).filter(JsonElement::isJsonArray).map(this::toStream).orElse(Stream.empty());
	}

	/**
	 * Converts an array of JsonElements to stream of JsonElements.
	 * 
	 * @param element
	 *            - must be JsonArray
	 * @return
	 */
	private Stream<JsonElement> toStream(JsonElement element) {
		JsonArray array = element.getAsJsonArray();
		Spliterator<JsonElement> spliterator = Spliterators.spliterator(array.iterator(), array.size(), 64);
		return StreamSupport.stream(spliterator, true); // true for parallel
	}
}
