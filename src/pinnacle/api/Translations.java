package pinnacle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pinnacle.api.Enums.LANGUAGE;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Translations {

	private String originalText;
	public String originalText () { return this.originalText; }
	
	private List<Translation> languages = new ArrayList<>();
	public List<Translation> languages () { return this.languages; }
	
	public static class Translation {
		
		private LANGUAGE language;
		public LANGUAGE language () { return this.language; }
		
		private String translatedText;
		public String translatedText () { return this.translatedText; }
		
		private Translation (Json json) {
			this.language = json.getAsString("id")
					.map(LANGUAGE::of)
					.orElseThrow(() -> NoNecessaryKeyException.of("Translation.languageCode"));
			this.translatedText = json.getAsString("text")
					.orElseThrow(() -> NoNecessaryKeyException.of("Translation.translatedText"));
		}
		
		static Translation parse (Json json) {
			return new Translation(json);
		}

		@Override
		public String toString() {
			return "Translation [language=" + language + ", translatedText="
					+ translatedText + "]";
		}
		
	}
	
	private Translations (Json json) {
		this.originalText = json.getAsString("text")
				.orElseThrow(() -> NoNecessaryKeyException.of("Translation.originalText"));
		this.languages = json.getAsJsonStream("cultures")
				.map(Translation::parse)
				.collect(Collectors.toList());
	}
	
	static List<Translations> parse (String text) throws PinnacleException {
		return text.equals("")
				? new ArrayList<>()
				: Json.of(text)
					.getAsJsonStream("translations")
					.map(Translations::new)
					.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Translations [originalText=" + originalText + ", languages="
				+ languages + "]";
	}
	
}
