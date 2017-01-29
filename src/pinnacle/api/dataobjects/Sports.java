package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class Sports extends AbstractDataObject {

	private List<Sport> sports = new ArrayList<>();

	public List<Sport> sports() {
		return this.sports;
	}

	public Stream<Sport> sportsAsStream() {
		return this.sports.stream();
	}

	private Sports(Json json) {
		this.sports = json.getAsJsonStream("sports").map(Sport::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Sports() {
		this.isEmpty = true;
	}

	public static Sports parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Sports() : new Sports(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Sports [sports=" + sports + "]";
	}

	public static class Sport extends AbstractDataObject {

		private Integer id;

		public Integer id() {
			return this.id;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private Boolean hasOfferings;

		public Boolean hasOfferings() {
			return this.hasOfferings;
		}

		private Integer leagueSpecialsCount;

		public Integer leagueSpecialsCount() {
			return this.leagueSpecialsCount;
		}

		private Integer eventSpecialsCount;

		public Integer eventSpecialsCount() {
			return this.eventSpecialsCount;
		}

		private Integer eventCount;

		public Integer eventCount() {
			return this.eventCount;
		}

		private Sport(Json json) {
			this.id = json.getAsInteger("id").orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.hasOfferings = json.getAsBoolean("hasOfferings").orElse(null);
			this.leagueSpecialsCount = json.getAsInteger("leagueSpecialsCount").orElse(null);
			this.eventSpecialsCount = json.getAsInteger("eventSpecialsCount").orElse(null);
			this.eventCount = json.getAsInteger("eventCount").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.name == null || this.hasOfferings == null || this.leagueSpecialsCount == null
					|| this.eventSpecialsCount == null || this.eventCount == null;
		}

		@Override
		public String toString() {
			return "Sport [id=" + id + ", name=" + name + ", hasOfferings=" + hasOfferings + ", leagueSpecialsCount="
					+ leagueSpecialsCount + ", eventSpecialsCount=" + eventSpecialsCount + ", eventCount=" + eventCount
					+ "]";
		}
	}
}
