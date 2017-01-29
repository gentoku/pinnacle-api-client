package pinnacle.api.enums;

public enum BET_TYPE {

	MONEYLINE,
	TEAM_TOTAL_POINTS,
	SPREAD,
	TOTAL_POINTS,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static BET_TYPE fromAPI (String text) {
		try {
			return BET_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return BET_TYPE.UNDEFINED;
		}
	}
}
