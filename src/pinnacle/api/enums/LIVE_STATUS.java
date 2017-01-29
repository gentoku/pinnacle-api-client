package pinnacle.api.enums;

import java.util.Arrays;

public enum LIVE_STATUS {

	NO_LIVE_BETTING("0"),
	LIVE_BETTING("1"),
	WILL_BE_OFFERED("2"),
	UNDEFINED("undefined");

	private final String value;
	
	private LIVE_STATUS (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static LIVE_STATUS fromAPI (String value) {
		return Arrays.stream(LIVE_STATUS.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(LIVE_STATUS.UNDEFINED);
	}
}
