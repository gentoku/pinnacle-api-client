package pinnacle.api.enums;

public enum BET_STATUS {

	ACCEPTED,
	CANCELLED,
	LOSE,
	PENDING_ACCEPTANCE,
	REFUNDED,
	REJECTED,
	WON,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static BET_STATUS fromAPI (String text) {
		try {
			return BET_STATUS.valueOf(text);
		} catch (IllegalArgumentException e) {
			return BET_STATUS.UNDEFINED;
		}
	}
}
