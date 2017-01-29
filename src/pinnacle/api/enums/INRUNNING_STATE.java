package pinnacle.api.enums;

import java.util.Arrays;

public enum INRUNNING_STATE {

	FIRST_HALF_IN_PROGRESS("1"),
	HALF_TIME_IN_PROGRESS("2"),
	SECOND_HALF_IN_PROGRESS("3"),
	END_OF_REGULAR_TIME("4"),
	EXTRA_FIRST_HALF_IN_PROGRESS("5"),
	EXTRA_HALF_TIME_IN_PROGRESS("6"),
	EXTRA_SECOND_HALF_IN_PROGRESS("7"),
	END_OF_EXTRA_TIME("8"),
	END_OF_GAME("9"),
	TEMPORARY_SUSPENDED("10"),
	PENALTIES_IN_PROGRESS("11"),
	UNDEFINED("undefined");

	private final String value;
	
	private INRUNNING_STATE (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static INRUNNING_STATE fromAPI (String value) {
		return Arrays.stream(INRUNNING_STATE.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(INRUNNING_STATE.UNDEFINED);
	}
}
