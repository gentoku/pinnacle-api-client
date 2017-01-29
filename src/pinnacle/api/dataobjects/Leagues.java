package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class Leagues extends AbstractDataObject {

	private List<League> leagues = new ArrayList<>();

	public List<League> leagues() {
		return this.leagues;
	}

	public Stream<League> leaguesAsStream() {
		return this.leagues.stream();
	}

	private Leagues(Json json) {
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Leagues() {
		this.isEmpty = true;
	}

	public static Leagues parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Leagues() : new Leagues(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Leagues [leagues=" + leagues + "]";
	}

	public static class League extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private String homeTeamType;

		public String homeTeamType() {
			return this.homeTeamType;
		}

		private Boolean hasOfferings;

		public Boolean hasOfferings() {
			return this.hasOfferings;
		}

		private Boolean allowRoundRobins;

		public Boolean allowRoundRobins() {
			return this.allowRoundRobins;
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

		private League(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.homeTeamType = json.getAsString("homeTeamType").orElse(null);
			this.hasOfferings = json.getAsBoolean("hasOfferings").orElse(null);
			this.allowRoundRobins = json.getAsBoolean("allowRoundRobins").orElse(null);
			this.leagueSpecialsCount = json.getAsInteger("leagueSpecialsCount").orElse(null);
			this.eventSpecialsCount = json.getAsInteger("eventSpecialsCount").orElse(null);
			this.eventCount = json.getAsInteger("eventCount").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.name == null || this.homeTeamType == null || this.hasOfferings == null
					|| this.allowRoundRobins == null || this.leagueSpecialsCount == null
					|| this.eventSpecialsCount == null || this.eventCount == null;
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", name=" + name + ", homeTeamType=" + homeTeamType + ", hasOfferings="
					+ hasOfferings + ", allowRoundRobins=" + allowRoundRobins + ", leagueSpecialsCount="
					+ leagueSpecialsCount + ", eventSpecialsCount=" + eventSpecialsCount + ", eventCount=" + eventCount
					+ "]";
		}
	}
}
