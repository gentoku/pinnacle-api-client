package pinnacle.api.enums;

public enum LEG_BET_STATUS {

	CANCELLED,
	LOSE ,
	PUSH,
	REFUNDED,
	REJECTED ,
	WON,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static LEG_BET_STATUS fromAPI (String text) {
		try {
			return LEG_BET_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return LEG_BET_STATUS.UNDEFINED;
		}
	}
}
