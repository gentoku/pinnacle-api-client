package pinnacle.api.enums;

public enum LEG_BET_TYPE {

	MONEYLINE,
	SPREAD  ,
	TOTAL_POINTS,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static LEG_BET_TYPE fromAPI (String text) {
		try {
			return LEG_BET_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return LEG_BET_TYPE.UNDEFINED;
		}
	}
}
