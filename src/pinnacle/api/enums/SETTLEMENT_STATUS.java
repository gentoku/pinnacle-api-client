package pinnacle.api.enums;

import java.util.Arrays;

public enum SETTLEMENT_STATUS {

	SETTLED("1"),
	RE_SETTLED("2"),
	CANCELLED("3"),
	RE_SETTLED_AS_CANCELLED("4"),
	DELETED("5"),
	UNDEFINED("undefined");

	private final String value;
	
	private SETTLEMENT_STATUS (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static SETTLEMENT_STATUS fromAPI (String value) {
		return Arrays.stream(SETTLEMENT_STATUS.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(SETTLEMENT_STATUS.UNDEFINED);
	}
}
