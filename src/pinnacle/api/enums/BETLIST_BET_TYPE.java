package pinnacle.api.enums;

public enum BETLIST_BET_TYPE {

	MONEYLINE,
	TEAM_TOTAL_POINTS,
	SPREAD,
	TOTAL_POINTS,
	SPECIAL,
	PARLAY,
	TEASER,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static BETLIST_BET_TYPE fromAPI (String text) {
		try {
			return BETLIST_BET_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return BETLIST_BET_TYPE.UNDEFINED;
		}
	}
}
