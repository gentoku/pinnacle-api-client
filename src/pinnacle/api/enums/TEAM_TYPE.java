package pinnacle.api.enums;

import java.util.Arrays;

public enum TEAM_TYPE {

	DRAW("Draw"),
	TEAM_1("Team1"),
	TEAM_2("Team2"),
	UNDEFINED("undefined");

	private final String value;
	
	private TEAM_TYPE (final String value) {
		this.value = value;
	}
	
	public String toAPI () {
		return this.value;
	}
	
	public static TEAM_TYPE fromAPI (String value) {
		return Arrays.stream(TEAM_TYPE.values())
		             .filter(e -> e.value.equals(value))
		             .findAny()
		             .orElse(TEAM_TYPE.UNDEFINED);
	}
}
