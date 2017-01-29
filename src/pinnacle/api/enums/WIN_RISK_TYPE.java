package pinnacle.api.enums;

public enum WIN_RISK_TYPE {

	RISK,
	WIN,
	UNDEFINED;

	public String toAPI () {
		return this.name();
	}
	
	public static WIN_RISK_TYPE fromAPI (String text) {
		try {
			return WIN_RISK_TYPE.valueOf(text);
		} catch (IllegalArgumentException e) {
			return WIN_RISK_TYPE.UNDEFINED;
		}
	}
}
