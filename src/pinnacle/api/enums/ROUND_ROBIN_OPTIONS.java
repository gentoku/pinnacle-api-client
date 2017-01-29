package pinnacle.api.enums;

import java.util.Arrays;

public enum ROUND_ROBIN_OPTIONS {

	SINGLE_PARLAY("Parlay"),
	BY_2_LEGS("TwoLegRoundRobin"),
	BY_3_LEGS("ThreeLegRoundRobin"),
	BY_4_LEGS("FourLegRoundRobin"),
	BY_5_LEGS("FiveLegRoundRobin"),
	BY_6_LEGS("SixLegRoundRobin"),
	BY_7_LEGS("SevenLegRoundRobin"),
	BY_8_LEGS("EightLegRoundRobin"),
	UNDEFINED("undefined");

	private final String value;
	
	private ROUND_ROBIN_OPTIONS (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static ROUND_ROBIN_OPTIONS fromAPI (String value) {
		return Arrays.stream(ROUND_ROBIN_OPTIONS.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(ROUND_ROBIN_OPTIONS.UNDEFINED);
	}
}
