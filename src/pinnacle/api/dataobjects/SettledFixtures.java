package pinnacle.api.dataobjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.SETTLEMENT_STATUS;

public class SettledFixtures extends AbstractDataObject {

	private Integer sportId;

	public Integer sportId() {
		return this.sportId;
	}

	private Long last;

	public Long last() {
		return this.last;
	}

	private List<League> leagues = new ArrayList<>();

	public List<League> leagues() {
		return this.leagues;
	}

	public Stream<League> leaguesAsStream() {
		return this.leagues.stream();
	}

	private SettledFixtures(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private SettledFixtures() {
		this.isEmpty = true;
	}

	public static SettledFixtures parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new SettledFixtures() : new SettledFixtures(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "SettledFixtures [sportId=" + sportId + ", last=" + last + ", leagues=" + leagues + "]";
	}

	public static class League extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private List<Event> events = new ArrayList<>();

		public List<Event> events() {
			return this.events;
		}

		public Stream<Event> eventsAsStream() {
			return this.events.stream();
		}

		private League(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.events = json.getAsJsonStream("events").map(Event::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", events=" + events + "]";
		}
	}

	public static class Event extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private List<Period> periods = new ArrayList<>();

		public List<Period> periods() {
			return this.periods;
		}

		public Stream<Period> periodsAsStream() {
			return this.periods.stream();
		}

		private Event(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.periods = json.getAsJsonStream("periods").map(Period::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "Event [id=" + id + ", periods=" + periods + "]";
		}
	}

	public static class Period extends AbstractDataObject {

		private Optional<Long> lineId;

		public Optional<Long> lineId() {
			return this.lineId;
		}

		private Integer number;

		public Integer number() {
			return this.number;
		}

		private Long settlementId;

		public Long settlementId() {
			return this.settlementId;
		}

		private Instant settledAt;

		public Instant settledAt() {
			return this.settledAt;
		}

		private SETTLEMENT_STATUS status;

		public SETTLEMENT_STATUS status() {
			return this.status;
		}

		private Integer team1Score;

		public Integer team1Score() {
			return this.team1Score;
		}

		private Integer team2Score;

		public Integer team2Score() {
			return this.team2Score;
		}

		private Optional<CancellationReason> cancellationReason;

		public Optional<CancellationReason> cancellationReason() {
			return this.cancellationReason;
		}

		private Period(Json json) {
			this.lineId = json.getAsLong("lineId");
			this.number = json.getAsInteger("number").orElse(null);
			this.settlementId = json.getAsLong("settlementId  ").orElse(null);
			this.settledAt = json.getAsString("settledAt ").map(Instant::parse).orElse(null);
			this.status = json.getAsString("status ").map(SETTLEMENT_STATUS::fromAPI).orElse(null);
			this.team1Score = json.getAsInteger("team1Score").orElse(null);
			this.team2Score = json.getAsInteger("team2Score").orElse(null);
			this.cancellationReason = json.getAsJson("cancellationReason").map(CancellationReason::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.number == null || this.settlementId == null || this.settledAt == null || this.status == null
					|| this.team1Score == null || this.team2Score == null;
		}

		@Override
		public String toString() {
			return "Period [lineId=" + lineId + ", number=" + number + ", settlementId  =" + settlementId
					+ ", settledAt =" + settledAt + ", status =" + status + ", team1Score=" + team1Score
					+ ", team2Score=" + team2Score + ", cancellationReason=" + cancellationReason + "]";
		}
	}

	public static class CancellationReason extends AbstractDataObject {

		private String code;

		public String code() {
			return this.code;
		}

		private Optional<CancellationSpecifics> details;

		public Optional<CancellationSpecifics> details() {
			return this.details;
		}

		private CancellationReason(Json json) {
			this.code = json.getAsString("code").orElse(null);
			this.details = json.getAsJson("details").map(CancellationSpecifics::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.code == null;
		}

		@Override
		public String toString() {
			return "CancellationReason [code=" + code + ", details=" + details + "]";
		}
	}

	public static class CancellationSpecifics extends AbstractDataObject {

		private Optional<String> correctTeam1Id;

		public Optional<String> correctTeam1Id() {
			return this.correctTeam1Id;
		}

		private Optional<String> correctTeam2Id;

		public Optional<String> correctTeam2Id() {
			return this.correctTeam2Id;
		}

		private Optional<String> correctListedPitcher1;

		public Optional<String> correctListedPitcher1() {
			return this.correctListedPitcher1;
		}

		private Optional<String> correctListedPitcher2;

		public Optional<String> correctListedPitcher2() {
			return this.correctListedPitcher2;
		}

		private Optional<String> correctSpread;

		public Optional<String> correctSpread() {
			return this.correctSpread;
		}

		private Optional<String> correctTotalPoints;

		public Optional<String> correctTotalPoints() {
			return this.correctTotalPoints;
		}

		private Optional<String> correctTeam1TotalPoints;

		public Optional<String> correctTeam1TotalPoints() {
			return this.correctTeam1TotalPoints;
		}

		private Optional<String> correctTeam2TotalPoints;

		public Optional<String> correctTeam2TotalPoints() {
			return this.correctTeam2TotalPoints;
		}

		private Optional<String> correctTeam1Score;

		public Optional<String> correctTeam1Score() {
			return this.correctTeam1Score;
		}

		private Optional<String> correctTeam2Score;

		public Optional<String> correctTeam2Score() {
			return this.correctTeam2Score;
		}

		private Optional<String> correctTeam1TennisSetsScore;

		public Optional<String> correctTeam1TennisSetsScore() {
			return this.correctTeam1TennisSetsScore;
		}

		private Optional<String> correctTeam2TennisSetsScore;

		public Optional<String> correctTeam2TennisSetsScore() {
			return this.correctTeam2TennisSetsScore;
		}

		private CancellationSpecifics(Json json) {
			this.correctTeam1Id = json.getAsString("correctTeam1Id");
			this.correctTeam2Id = json.getAsString("correctTeam2Id");
			this.correctListedPitcher1 = json.getAsString("correctListedPitcher1");
			this.correctListedPitcher2 = json.getAsString("correctListedPitcher2");
			this.correctSpread = json.getAsString("correctSpread");
			this.correctTotalPoints = json.getAsString("correctTotalPoints");
			this.correctTeam1TotalPoints = json.getAsString("correctTeam1TotalPoints");
			this.correctTeam2TotalPoints = json.getAsString("correctTeam2TotalPoints");
			this.correctTeam1Score = json.getAsString("correctTeam1Score");
			this.correctTeam2Score = json.getAsString("correctTeam2Score");
			this.correctTeam1TennisSetsScore = json.getAsString("correctTeam1TennisSetsScore");
			this.correctTeam2TennisSetsScore = json.getAsString("correctTeam2TennisSetsScore");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return false;
		}

		@Override
		public String toString() {
			return "CancellationSpecifics [correctTeam1Id=" + correctTeam1Id + ", correctTeam2Id=" + correctTeam2Id
					+ ", correctListedPitcher1=" + correctListedPitcher1 + ", correctListedPitcher2="
					+ correctListedPitcher2 + ", correctSpread=" + correctSpread + ", correctTotalPoints="
					+ correctTotalPoints + ", correctTeam1TotalPoints=" + correctTeam1TotalPoints
					+ ", correctTeam2TotalPoints=" + correctTeam2TotalPoints + ", correctTeam1Score="
					+ correctTeam1Score + ", correctTeam2Score=" + correctTeam2Score + ", correctTeam1TennisSetsScore="
					+ correctTeam1TennisSetsScore + ", correctTeam2TennisSetsScore=" + correctTeam2TennisSetsScore
					+ "]";
		}
	}
}
