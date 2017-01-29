package pinnacle.api.enums;

import java.util.Arrays;

public enum CULTURE_CODE {
	
	ENGLISH("en-US"),
	BRITISH("en-GB"),
	CHINESE_SI("zh-CN"),
	CHINESE_TR("zh-TW"),
	FINNISH("fi-FI"),
	GERMAN("de-DE"),
	HEBREW("he-IL"),
	ITALIAN("it-IT"),
	NORWEGIAN("nb-NO"),
	PORTUGUESE("pt-BR"),
	RUSSIAN("ru-RU"),
	SPANISH("es-ES"),
	SWEDISH("sv-SE"),
	THAI("th-TH"),
	POLISH("pl-PL"),
	FRENCH("fr-FR"),
	JAPANESE("ja-JP"),
	KOREAN("ko-KR"),
	VIETNAMESE("vi-VN"),
	INDONESIAN("id-ID"),
	CZECH("cs-CZ"),
	UNDEFINED("undefined");
	
	private final String value;

	private CULTURE_CODE(final String value) {
		this.value = value;
	}

	public String toAPI() {
		return this.value;
	}

	public static CULTURE_CODE fromAPI(String value) {
		return Arrays.stream(CULTURE_CODE.values()).filter(e -> e.value.equals(value)).findAny()
				.orElse(CULTURE_CODE.UNDEFINED);
	}
}
