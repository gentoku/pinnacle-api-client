package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class Translations extends AbstractDataObject {

	private List<Translation> translations = new ArrayList<>();

	public List<Translation> translations() {
		return this.translations;
	}

	public Stream<Translation> translationsAsStream() {
		return this.translations.stream();
	}

	private Translations(Json json) {
		this.translations = json.getAsJsonStream("translations").map(Translation::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Translations() {
		this.isEmpty = true;
	}

	public static Translations parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Translations() : new Translations(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Translations [translations=" + translations + "]";
	}

	public static class Translation extends AbstractDataObject {

		private String text;

		public String text() {
			return this.text;
		}

		private List<Culture> cultures = new ArrayList<>();

		public List<Culture> cultures() {
			return this.cultures;
		}

		public Stream<Culture> culturesAsStream() {
			return this.cultures.stream();
		}

		private Translation(Json json) {
			this.text = json.getAsString("text").orElse(null);
			this.cultures = json.getAsJsonStream("cultures").map(Culture::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.text == null;
		}

		@Override
		public String toString() {
			return "Translation [text=" + text + ", cultures=" + cultures + "]";
		}
	}

	public static class Culture extends AbstractDataObject {

		private String id;

		public String id() {
			return this.id;
		}

		private String text;

		public String text() {
			return this.text;
		}

		private Culture(Json json) {
			this.id = json.getAsString("id").orElse(null);
			this.text = json.getAsString("text").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.text == null;
		}

		@Override
		public String toString() {
			return "Culture [id=" + id + ", text=" + text + "]";
		}
	}
}
