package pinnacle.api.enums;

import java.util.Arrays;

public enum FEED_ODDS_FORMAT_TYPE {

	AMERICAN("0"),
	DECIMAL("1"),
	HONGKONG("2"),
	INDONESIAN("3"),
	MALAY("4"),
	FRACTION("5"),
	UNDEFINED("undefined");

	private final String value;
	
	private FEED_ODDS_FORMAT_TYPE (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static FEED_ODDS_FORMAT_TYPE fromAPI (String value) {
		return Arrays.stream(FEED_ODDS_FORMAT_TYPE.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(FEED_ODDS_FORMAT_TYPE.UNDEFINED);
	}
}
