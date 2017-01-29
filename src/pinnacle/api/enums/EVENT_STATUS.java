package pinnacle.api.enums;

import java.util.Arrays;

public enum EVENT_STATUS {

	LINES_UNAVAILABLE_TEMPORARILY("H"),
	LINES_WITH_RED_CIRCLE("I"),
	LINES_OPEN_FOR_BETTING("O"),
	UNDEFINED("undefined");

	private final String value;
	
	private EVENT_STATUS (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static EVENT_STATUS fromAPI (String value) {
		return Arrays.stream(EVENT_STATUS.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(EVENT_STATUS.UNDEFINED);
	}
}
