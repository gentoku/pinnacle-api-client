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

public class SettledSpecialFixtures extends AbstractDataObject {

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

	private SettledSpecialFixtures(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private SettledSpecialFixtures() {
		this.isEmpty = true;
	}

	public static SettledSpecialFixtures parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new SettledSpecialFixtures() : new SettledSpecialFixtures(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "SettledSpecialFixtures [sportId=" + sportId + ", last=" + last + ", leagues=" + leagues + "]";
	}

	public static class League extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private List<Special> specials = new ArrayList<>();

		public List<Special> specials() {
			return this.specials;
		}

		public Stream<Special> specialsAsStream() {
			return this.specials.stream();
		}

		private League(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.specials = json.getAsJsonStream("specials").map(Special::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", specials=" + specials + "]";
		}
	}

	public static class Special extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private SETTLEMENT_STATUS status;

		public SETTLEMENT_STATUS status() {
			return this.status;
		}

		private Long settlementId;

		public Long settlementId() {
			return this.settlementId;
		}

		private Instant settledAt;

		public Instant settledAt() {
			return this.settledAt;
		}

		private Optional<CancellationReason> cancellationReason;

		public Optional<CancellationReason> cancellationReason() {
			return this.cancellationReason;
		}

		private Special(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.status = json.getAsString("status").map(SETTLEMENT_STATUS::fromAPI).orElse(null);
			this.settlementId = json.getAsLong("settlementId").orElse(null);
			this.settledAt = json.getAsString("settledAt").map(Instant::parse).orElse(null);
			this.cancellationReason = json.getAsJson("cancellationReason").map(CancellationReason::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.status == null || this.settlementId == null || this.settledAt == null;
		}

		@Override
		public String toString() {
			return "Special [id=" + id + ", status=" + status + ", settlementId=" + settlementId + ", settledAt="
					+ settledAt + ", cancellationReason=" + cancellationReason + "]";
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
