package pinnacle.api.enums;

import java.util.Arrays;

public enum PARLAY_RESTRICTION {

	ALLOWED_WITHOUT_RESTRICTIONS("0"),
	NOT_ALLOWED("1"),
	ALLOWED_WITH_RESTRICTIONS("2"),
	UNDEFINED("undefined");

	private final String value;
	
	private PARLAY_RESTRICTION (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static PARLAY_RESTRICTION fromAPI (String value) {
		return Arrays.stream(PARLAY_RESTRICTION.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(PARLAY_RESTRICTION.UNDEFINED);
	}
}
