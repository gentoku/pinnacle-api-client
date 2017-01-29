package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class Periods extends AbstractDataObject {

	private List<Period> periods = new ArrayList<>();

	public List<Period> periods() {
		return this.periods;
	}

	public Stream<Period> periodsAsStream() {
		return this.periods.stream();
	}

	private Periods(Json json) {
		this.periods = json.getAsJsonStream("periods").map(Period::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Periods() {
		this.isEmpty = true;
	}

	public static Periods parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Periods() : new Periods(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Periods [periods=" + periods + "]";
	}

	public static class Period extends AbstractDataObject {

		private Integer number;

		public Integer number() {
			return this.number;
		}

		private Optional<String> description;

		public Optional<String> description() {
			return this.description;
		}

		private Optional<String> shortDescription;

		public Optional<String> shortDescription() {
			return this.shortDescription;
		}

		private Optional<String> spreadDescription;

		public Optional<String> spreadDescription() {
			return this.spreadDescription;
		}

		private Optional<String> moneylineDescription;

		public Optional<String> moneylineDescription() {
			return this.moneylineDescription;
		}

		private Optional<String> totalDescription;

		public Optional<String> totalDescription() {
			return this.totalDescription;
		}

		private Optional<String> team1TotalDescription;

		public Optional<String> team1TotalDescription() {
			return this.team1TotalDescription;
		}

		private Optional<String> team2TotalDescription;

		public Optional<String> team2TotalDescription() {
			return this.team2TotalDescription;
		}

		private Optional<String> spreadShortDescription;

		public Optional<String> spreadShortDescription() {
			return this.spreadShortDescription;
		}

		private Optional<String> moneylineShortDescription;

		public Optional<String> moneylineShortDescription() {
			return this.moneylineShortDescription;
		}

		private Optional<String> totalShortDescription;

		public Optional<String> totalShortDescription() {
			return this.totalShortDescription;
		}

		private Optional<String> team1TotalShortDescription;

		public Optional<String> team1TotalShortDescription() {
			return this.team1TotalShortDescription;
		}

		private Optional<String> team2TotalShortDescription;

		public Optional<String> team2TotalShortDescription() {
			return this.team2TotalShortDescription;
		}

		private Period(Json json) {
			this.number = json.getAsInteger("number").orElse(null);
			this.description = json.getAsString("description");
			this.shortDescription = json.getAsString("shortDescription");
			this.spreadDescription = json.getAsString("spreadDescription");
			this.moneylineDescription = json.getAsString("moneylineDescription");
			this.totalDescription = json.getAsString("totalDescription");
			this.team1TotalDescription = json.getAsString("team1TotalDescription");
			this.team2TotalDescription = json.getAsString("team2TotalDescription");
			this.spreadShortDescription = json.getAsString("spreadShortDescription");
			this.moneylineShortDescription = json.getAsString("moneylineShortDescription");
			this.totalShortDescription = json.getAsString("totalShortDescription");
			this.team1TotalShortDescription = json.getAsString("team1TotalShortDescription");
			this.team2TotalShortDescription = json.getAsString("team2TotalShortDescription");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.number == null;
		}

		@Override
		public String toString() {
			return "Period [number=" + number + ", description=" + description + ", shortDescription="
					+ shortDescription + ", spreadDescription=" + spreadDescription + ", moneylineDescription="
					+ moneylineDescription + ", totalDescription=" + totalDescription + ", team1TotalDescription="
					+ team1TotalDescription + ", team2TotalDescription=" + team2TotalDescription
					+ ", spreadShortDescription=" + spreadShortDescription + ", moneylineShortDescription="
					+ moneylineShortDescription + ", totalShortDescription=" + totalShortDescription
					+ ", team1TotalShortDescription=" + team1TotalShortDescription + ", team2TotalShortDescription="
					+ team2TotalShortDescription + "]";
		}
	}
}
