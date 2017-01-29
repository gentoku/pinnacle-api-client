package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.BETLIST_BET_TYPE;
import pinnacle.api.enums.BET_STATUS;
import pinnacle.api.enums.LEG_BET_STATUS;
import pinnacle.api.enums.LEG_BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.SIDE_TYPE;

public class Bets extends AbstractDataObject {

	private List<StraightBet> straightBets = new ArrayList<>();

	public List<StraightBet> straightBets() {
		return this.straightBets;
	}

	public Stream<StraightBet> straightBetsAsStream() {
		return this.straightBets.stream();
	}

	private List<ParlayBet> parlayBets = new ArrayList<>();

	public List<ParlayBet> parlayBets() {
		return this.parlayBets;
	}

	public Stream<ParlayBet> parlayBetsAsStream() {
		return this.parlayBets.stream();
	}

	private List<TeaserBet> teaserBets = new ArrayList<>();

	public List<TeaserBet> teaserBets() {
		return this.teaserBets;
	}

	public Stream<TeaserBet> teaserBetsAsStream() {
		return this.teaserBets.stream();
	}

	private List<SpecialBet> specialBets = new ArrayList<>();

	public List<SpecialBet> specialBets() {
		return this.specialBets;
	}

	public Stream<SpecialBet> specialBetsAsStream() {
		return this.specialBets.stream();
	}

	private Bets(Json json) {
		json.getAsJsonStream("bets").forEach(bet -> {
			BETLIST_BET_TYPE type = bet.getAsString("betType").map(BETLIST_BET_TYPE::fromAPI).orElse(null);
			if (type == BETLIST_BET_TYPE.SPREAD || type == BETLIST_BET_TYPE.MONEYLINE
					|| type == BETLIST_BET_TYPE.TOTAL_POINTS || type == BETLIST_BET_TYPE.TEAM_TOTAL_POINTS) {
				this.straightBets.add(new StraightBet(bet));
			} else if (type == BETLIST_BET_TYPE.PARLAY) {
				this.parlayBets.add(new ParlayBet(bet));
			} else if (type == BETLIST_BET_TYPE.TEASER) {
				this.teaserBets.add(new TeaserBet(bet));
			} else if (type == BETLIST_BET_TYPE.SPECIAL) {
				this.specialBets.add(new SpecialBet(bet));
			} else if (type == BETLIST_BET_TYPE.UNDEFINED || type == null) {
				this.isFlawed = true;
			}
		});
	}

	private Bets() {
		this.isEmpty = true;
	}

	public static Bets parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Bets() : new Bets(Json.of(jsonText));
	}

	private boolean isAllListsEmpty() {
		return this.straightBets.size() == 0 && this.parlayBets.size() == 0 
				&& this.teaserBets.size() == 0 && this.specialBets.size() == 0;
	}
	
	@Override
	public boolean isEmpty() {
		return this.isAllListsEmpty() || this.isEmpty;
	}
	
	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Bets [straightBets=" + straightBets + ", parlayBets=" + parlayBets + ", teaserBets=" + teaserBets
				+ ", specialBets=" + specialBets + "]";
	}

	public static class StraightBet extends AbstractDataObject {

		private Long betId;

		public Long betId() {
			return this.betId;
		}

		private Integer wagerNumber;

		public Integer wagerNumber() {
			return this.wagerNumber;
		}

		private Instant placedAt;

		public Instant placedAt() {
			return this.placedAt;
		}

		private BigDecimal win;

		public BigDecimal win() {
			return this.win;
		}

		private BigDecimal risk;

		public BigDecimal risk() {
			return this.risk;
		}

		private Optional<BigDecimal> winLoss;

		public Optional<BigDecimal> winLoss() {
			return this.winLoss;
		}

		private BET_STATUS betStatus;

		public BET_STATUS betStatus() {
			return this.betStatus;
		}

		private BETLIST_BET_TYPE betType;

		public BETLIST_BET_TYPE betType() {
			return this.betType;
		}

		private Integer sportId;

		public Integer sportId() {
			return this.sportId;
		}

		private Long leagueId;

		public Long leagueId() {
			return this.leagueId;
		}

		private Long eventId;

		public Long eventId() {
			return this.eventId;
		}

		private Optional<BigDecimal> handicap;

		public Optional<BigDecimal> handicap() {
			return this.handicap;
		}

		private BigDecimal price;

		public BigDecimal price() {
			return this.price;
		}

		private Optional<String> teamName;

		public Optional<String> teamName() {
			return this.teamName;
		}

		private Optional<SIDE_TYPE> side;

		public Optional<SIDE_TYPE> side() {
			return this.side;
		}

		private ODDS_FORMAT oddsFormat;

		public ODDS_FORMAT oddsFormat() {
			return this.oddsFormat;
		}

		private Optional<BigDecimal> customerCommission;

		public Optional<BigDecimal> customerCommission() {
			return this.customerCommission;
		}

		private Optional<String> pitcher1;

		public Optional<String> pitcher1() {
			return this.pitcher1;
		}

		private Optional<String> pitcher2;

		public Optional<String> pitcher2() {
			return this.pitcher2;
		}

		private Optional<Boolean> pitcher1MustStart;

		public Optional<Boolean> pitcher1MustStart() {
			return this.pitcher1MustStart;
		}

		private Optional<Boolean> pitcher2MustStart;

		public Optional<Boolean> pitcher2MustStart() {
			return this.pitcher2MustStart;
		}

		private String team1;

		public String team1() {
			return this.team1;
		}

		private String team2;

		public String team2() {
			return this.team2;
		}

		private Optional<Boolean> isLive;

		public Optional<Boolean> isLive() {
			return this.isLive;
		}

		private Optional<Integer> periodNumber;

		public Optional<Integer> periodNumber() {
			return this.periodNumber;
		}

		private Optional<BigDecimal> team1Score;

		public Optional<BigDecimal> team1Score() {
			return this.team1Score;
		}

		private Optional<BigDecimal> team2Score;

		public Optional<BigDecimal> team2Score() {
			return this.team2Score;
		}

		private Optional<BigDecimal> ftTeam1Score;

		public Optional<BigDecimal> ftTeam1Score() {
			return this.ftTeam1Score;
		}

		private Optional<BigDecimal> ftTeam2Score;

		public Optional<BigDecimal> ftTeam2Score() {
			return this.ftTeam2Score;
		}

		private Optional<BigDecimal> pTeam1Score;

		public Optional<BigDecimal> pTeam1Score() {
			return this.pTeam1Score;
		}

		private Optional<BigDecimal> pTeam2Score;

		public Optional<BigDecimal> pTeam2Score() {
			return this.pTeam2Score;
		}

		private Optional<CancellationReason> cancellationReason;

		public Optional<CancellationReason> cancellationReason() {
			return this.cancellationReason;
		}

		private StraightBet(Json json) {
			this.betId = json.getAsLong("betId").orElse(null);
			this.wagerNumber = json.getAsInteger("wagerNumber").orElse(null);
			this.placedAt = json.getAsString("placedAt").map(s -> s + "Z").map(Instant::parse).orElse(null);
			this.win = json.getAsBigDecimal("win").orElse(null);
			this.risk = json.getAsBigDecimal("risk").orElse(null);
			this.winLoss = json.getAsBigDecimal("winLoss");
			this.betStatus = json.getAsString("betStatus").map(BET_STATUS::fromAPI).orElse(null);
			this.betType = json.getAsString("betType").map(BETLIST_BET_TYPE::fromAPI).orElse(null);
			this.sportId = json.getAsInteger("sportId").orElse(null);
			this.leagueId = json.getAsLong("leagueId").orElse(null);
			this.eventId = json.getAsLong("eventId").orElse(null);
			this.handicap = json.getAsBigDecimal("handicap");
			this.price = json.getAsBigDecimal("price").orElse(null);
			this.teamName = json.getAsString("teamName");
			this.side = json.getAsString("side").map(SIDE_TYPE::fromAPI);
			this.oddsFormat = json.getAsString("oddsFormat").map(ODDS_FORMAT::fromAPI).orElse(null);
			this.customerCommission = json.getAsBigDecimal("customerCommission");
			this.pitcher1 = json.getAsString("pitcher1");
			this.pitcher2 = json.getAsString("pitcher2");
			this.pitcher1MustStart = json.getAsBoolean("pitcher1MustStart");
			this.pitcher2MustStart = json.getAsBoolean("pitcher2MustStart");
			this.team1 = json.getAsString("team1").orElse(null);
			this.team2 = json.getAsString("team2").orElse(null);
			this.isLive = json.getAsBoolean("isLive");
			this.periodNumber = json.getAsInteger("periodNumber");
			this.team1Score = json.getAsBigDecimal("team1Score");
			this.team2Score = json.getAsBigDecimal("team2Score");
			this.ftTeam1Score = json.getAsBigDecimal("ftTeam1Score");
			this.ftTeam2Score = json.getAsBigDecimal("ftTeam2Score");
			this.pTeam1Score = json.getAsBigDecimal("pTeam1Score");
			this.pTeam2Score = json.getAsBigDecimal("pTeam2Score");
			this.cancellationReason = json.getAsJson("cancellationReason").map(CancellationReason::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.betId == null || this.wagerNumber == null || this.placedAt == null || this.win == null
					|| this.risk == null || this.betStatus == null || this.betType == null || this.sportId == null
					|| this.leagueId == null || this.eventId == null || this.price == null || this.oddsFormat == null
					|| this.team1 == null || this.team2 == null;
		}

		@Override
		public String toString() {
			return "StraightBet [betId=" + betId + ", wagerNumber=" + wagerNumber + ", placedAt=" + placedAt + ", win="
					+ win + ", risk=" + risk + ", winLoss=" + winLoss + ", betStatus=" + betStatus + ", betType="
					+ betType + ", sportId=" + sportId + ", leagueId=" + leagueId + ", eventId=" + eventId
					+ ", handicap=" + handicap + ", price=" + price + ", teamName=" + teamName + ", side=" + side
					+ ", oddsFormat=" + oddsFormat + ", customerCommission=" + customerCommission + ", pitcher1="
					+ pitcher1 + ", pitcher2=" + pitcher2 + ", pitcher1MustStart=" + pitcher1MustStart
					+ ", pitcher2MustStart=" + pitcher2MustStart + ", team1=" + team1 + ", team2=" + team2 + ", isLive="
					+ isLive + ", periodNumber=" + periodNumber + ", team1Score=" + team1Score + ", team2Score="
					+ team2Score + ", ftTeam1Score=" + ftTeam1Score + ", ftTeam2Score=" + ftTeam2Score
					+ ", pTeam1Score=" + pTeam1Score + ", pTeam2Score=" + pTeam2Score + ", cancellationReason="
					+ cancellationReason + "]";
		}
	}

	public static class ParlayBet extends AbstractDataObject {

		private Long betId;

		public Long betId() {
			return this.betId;
		}

		private Integer wagerNumber;

		public Integer wagerNumber() {
			return this.wagerNumber;
		}

		private Instant placedAt;

		public Instant placedAt() {
			return this.placedAt;
		}

		private BigDecimal win;

		public BigDecimal win() {
			return this.win;
		}

		private BigDecimal risk;

		public BigDecimal risk() {
			return this.risk;
		}

		private Optional<BigDecimal> winLoss;

		public Optional<BigDecimal> winLoss() {
			return this.winLoss;
		}

		private BET_STATUS betStatus;

		public BET_STATUS betStatus() {
			return this.betStatus;
		}

		private BETLIST_BET_TYPE betType;

		public BETLIST_BET_TYPE betType() {
			return this.betType;
		}

		private ODDS_FORMAT oddsFormat;

		public ODDS_FORMAT oddsFormat() {
			return this.oddsFormat;
		}

		private Optional<BigDecimal> customerCommission;

		public Optional<BigDecimal> customerCommission() {
			return this.customerCommission;
		}

		private List<ParlayBetLeg> Legs = new ArrayList<>();

		public List<ParlayBetLeg> Legs() {
			return this.Legs;
		}

		public Stream<ParlayBetLeg> LegsAsStream() {
			return this.Legs.stream();
		}

		private ParlayBet(Json json) {
			this.betId = json.getAsLong("betId").orElse(null);
			this.wagerNumber = json.getAsInteger("wagerNumber").orElse(null);
			this.placedAt = json.getAsString("placedAt").map(s -> s + "Z").map(Instant::parse).orElse(null);
			this.win = json.getAsBigDecimal("win").orElse(null);
			this.risk = json.getAsBigDecimal("risk").orElse(null);
			this.winLoss = json.getAsBigDecimal("winLoss");
			this.betStatus = json.getAsString("betStatus").map(BET_STATUS::fromAPI).orElse(null);
			this.betType = json.getAsString("betType").map(BETLIST_BET_TYPE::fromAPI).orElse(null);
			this.oddsFormat = json.getAsString("oddsFormat").map(ODDS_FORMAT::fromAPI).orElse(null);
			this.customerCommission = json.getAsBigDecimal("customerCommission");
			this.Legs = json.getAsJsonStream("Legs").map(ParlayBetLeg::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.betId == null || this.wagerNumber == null || this.placedAt == null || this.win == null
					|| this.risk == null || this.betStatus == null || this.betType == null || this.oddsFormat == null;
		}

		@Override
		public String toString() {
			return "ParlayBet [betId=" + betId + ", wagerNumber=" + wagerNumber + ", placedAt=" + placedAt + ", win="
					+ win + ", risk=" + risk + ", winLoss=" + winLoss + ", betStatus=" + betStatus + ", betType="
					+ betType + ", oddsFormat=" + oddsFormat + ", customerCommission=" + customerCommission + ", Legs="
					+ Legs + "]";
		}
	}

	public static class ParlayBetLeg extends AbstractDataObject {

		private Integer sportId;

		public Integer sportId() {
			return this.sportId;
		}

		private Long leagueId;

		public Long leagueId() {
			return this.leagueId;
		}

		private Long eventId;

		public Long eventId() {
			return this.eventId;
		}

		private LEG_BET_TYPE legBetType;

		public LEG_BET_TYPE legBetType() {
			return this.legBetType;
		}

		private LEG_BET_STATUS legBetStatus;

		public LEG_BET_STATUS legBetStatus() {
			return this.legBetStatus;
		}

		private Optional<BigDecimal> handicap;

		public Optional<BigDecimal> handicap() {
			return this.handicap;
		}

		private BigDecimal price;

		public BigDecimal price() {
			return this.price;
		}

		private Optional<String> teamName;

		public Optional<String> teamName() {
			return this.teamName;
		}

		private Optional<SIDE_TYPE> side;

		public Optional<SIDE_TYPE> side() {
			return this.side;
		}

		private Optional<String> pitcher1;

		public Optional<String> pitcher1() {
			return this.pitcher1;
		}

		private Optional<String> pitcher2;

		public Optional<String> pitcher2() {
			return this.pitcher2;
		}

		private Optional<Boolean> pitcher1MustStart;

		public Optional<Boolean> pitcher1MustStart() {
			return this.pitcher1MustStart;
		}

		private Optional<Boolean> pitcher2MustStart;

		public Optional<Boolean> pitcher2MustStart() {
			return this.pitcher2MustStart;
		}

		private String team1;

		public String team1() {
			return this.team1;
		}

		private String team2;

		public String team2() {
			return this.team2;
		}

		private Optional<Boolean> isLive;

		public Optional<Boolean> isLive() {
			return this.isLive;
		}

		private Optional<Integer> periodNumber;

		public Optional<Integer> periodNumber() {
			return this.periodNumber;
		}

		private Optional<BigDecimal> team1Score;

		public Optional<BigDecimal> team1Score() {
			return this.team1Score;
		}

		private Optional<BigDecimal> team2Score;

		public Optional<BigDecimal> team2Score() {
			return this.team2Score;
		}

		private Optional<BigDecimal> ftTeam1Score;

		public Optional<BigDecimal> ftTeam1Score() {
			return this.ftTeam1Score;
		}

		private Optional<BigDecimal> ftTeam2Score;

		public Optional<BigDecimal> ftTeam2Score() {
			return this.ftTeam2Score;
		}

		private Optional<BigDecimal> pTeam1Score;

		public Optional<BigDecimal> pTeam1Score() {
			return this.pTeam1Score;
		}

		private Optional<BigDecimal> pTeam2Score;

		public Optional<BigDecimal> pTeam2Score() {
			return this.pTeam2Score;
		}

		private ParlayBetLeg(Json json) {
			this.sportId = json.getAsInteger("sportId").orElse(null);
			this.leagueId = json.getAsLong("leagueId").orElse(null);
			this.eventId = json.getAsLong("eventId").orElse(null);
			this.legBetType = json.getAsString("legBetType").map(LEG_BET_TYPE::fromAPI).orElse(null);
			this.legBetStatus = json.getAsString("legBetStatus").map(LEG_BET_STATUS::fromAPI).orElse(null);
			this.handicap = json.getAsBigDecimal("handicap");
			this.price = json.getAsBigDecimal("price").orElse(null);
			this.teamName = json.getAsString("teamName");
			this.side = json.getAsString("side").map(SIDE_TYPE::fromAPI);
			this.pitcher1 = json.getAsString("pitcher1");
			this.pitcher2 = json.getAsString("pitcher2");
			this.pitcher1MustStart = json.getAsBoolean("pitcher1MustStart");
			this.pitcher2MustStart = json.getAsBoolean("pitcher2MustStart");
			this.team1 = json.getAsString("team1").orElse(null);
			this.team2 = json.getAsString("team2").orElse(null);
			this.isLive = json.getAsBoolean("isLive");
			this.periodNumber = json.getAsInteger("periodNumber");
			this.team1Score = json.getAsBigDecimal("team1Score");
			this.team2Score = json.getAsBigDecimal("team2Score");
			this.ftTeam1Score = json.getAsBigDecimal("ftTeam1Score");
			this.ftTeam2Score = json.getAsBigDecimal("ftTeam2Score");
			this.pTeam1Score = json.getAsBigDecimal("pTeam1Score");
			this.pTeam2Score = json.getAsBigDecimal("pTeam2Score");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.sportId == null || this.leagueId == null || this.eventId == null || this.legBetType == null
					|| this.legBetStatus == null || this.price == null || this.team1 == null || this.team2 == null;
		}

		@Override
		public String toString() {
			return "ParlayBetLeg [sportId=" + sportId + ", leagueId=" + leagueId + ", eventId=" + eventId
					+ ", legBetType=" + legBetType + ", legBetStatus=" + legBetStatus + ", handicap=" + handicap
					+ ", price=" + price + ", teamName=" + teamName + ", side=" + side + ", pitcher1=" + pitcher1
					+ ", pitcher2=" + pitcher2 + ", pitcher1MustStart=" + pitcher1MustStart + ", pitcher2MustStart="
					+ pitcher2MustStart + ", team1=" + team1 + ", team2=" + team2 + ", isLive=" + isLive
					+ ", periodNumber=" + periodNumber + ", team1Score=" + team1Score + ", team2Score=" + team2Score
					+ ", ftTeam1Score=" + ftTeam1Score + ", ftTeam2Score=" + ftTeam2Score + ", pTeam1Score="
					+ pTeam1Score + ", pTeam2Score=" + pTeam2Score + "]";
		}
	}

	public static class TeaserBet extends AbstractDataObject {

		private Long betId;

		public Long betId() {
			return this.betId;
		}

		private Integer wagerNumber;

		public Integer wagerNumber() {
			return this.wagerNumber;
		}

		private Instant placedAt;

		public Instant placedAt() {
			return this.placedAt;
		}

		private BET_STATUS betStatus;

		public BET_STATUS betStatus() {
			return this.betStatus;
		}

		private BETLIST_BET_TYPE betType;

		public BETLIST_BET_TYPE betType() {
			return this.betType;
		}

		private BigDecimal win;

		public BigDecimal win() {
			return this.win;
		}

		private BigDecimal risk;

		public BigDecimal risk() {
			return this.risk;
		}

		private Optional<BigDecimal> winLoss;

		public Optional<BigDecimal> winLoss() {
			return this.winLoss;
		}

		private Optional<BigDecimal> customerCommission;

		public Optional<BigDecimal> customerCommission() {
			return this.customerCommission;
		}

		private String teaserName;

		public String teaserName() {
			return this.teaserName;
		}

		private Boolean isSameEventOnly;

		public Boolean isSameEventOnly() {
			return this.isSameEventOnly;
		}

		private Integer minPicks;

		public Integer minPicks() {
			return this.minPicks;
		}

		private Integer maxPicks;

		public Integer maxPicks() {
			return this.maxPicks;
		}

		private List<TeaserBetLeg> legs = new ArrayList<>();

		public List<TeaserBetLeg> legs() {
			return this.legs;
		}

		public Stream<TeaserBetLeg> legsAsStream() {
			return this.legs.stream();
		}

		private TeaserBet(Json json) {
			this.betId = json.getAsLong("betId").orElse(null);
			this.wagerNumber = json.getAsInteger("wagerNumber").orElse(null);
			this.placedAt = json.getAsString("placedAt").map(s -> s + "Z").map(Instant::parse).orElse(null);
			this.betStatus = json.getAsString("betStatus").map(BET_STATUS::fromAPI).orElse(null);
			this.betType = json.getAsString("betType").map(BETLIST_BET_TYPE::fromAPI).orElse(null);
			this.win = json.getAsBigDecimal("win").orElse(null);
			this.risk = json.getAsBigDecimal("risk").orElse(null);
			this.winLoss = json.getAsBigDecimal("winLoss");
			this.customerCommission = json.getAsBigDecimal("customerCommission");
			this.teaserName = json.getAsString("teaserName").orElse(null);
			this.isSameEventOnly = json.getAsBoolean("isSameEventOnly").orElse(null);
			this.minPicks = json.getAsInteger("minPicks").orElse(null);
			this.maxPicks = json.getAsInteger("maxPicks").orElse(null);
			this.legs = json.getAsJsonStream("legs").map(TeaserBetLeg::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.betId == null || this.wagerNumber == null || this.placedAt == null || this.betStatus == null
					|| this.betType == null || this.win == null || this.risk == null || this.teaserName == null
					|| this.isSameEventOnly == null || this.minPicks == null || this.maxPicks == null;
		}

		@Override
		public String toString() {
			return "TeaserBet [betId=" + betId + ", wagerNumber=" + wagerNumber + ", placedAt=" + placedAt
					+ ", betStatus=" + betStatus + ", betType=" + betType + ", win=" + win + ", risk=" + risk
					+ ", winLoss=" + winLoss + ", customerCommission=" + customerCommission + ", teaserName="
					+ teaserName + ", isSameEventOnly=" + isSameEventOnly + ", minPicks=" + minPicks + ", maxPicks="
					+ maxPicks + ", legs=" + legs + "]";
		}
	}

	public static class TeaserBetLeg extends AbstractDataObject {

		private LEG_BET_TYPE legBetType;

		public LEG_BET_TYPE legBetType() {
			return this.legBetType;
		}

		private LEG_BET_STATUS legBetStatus;

		public LEG_BET_STATUS legBetStatus() {
			return this.legBetStatus;
		}

		private Long eventId;

		public Long eventId() {
			return this.eventId;
		}

		private Instant eventStartTime;

		public Instant eventStartTime() {
			return this.eventStartTime;
		}

		private Integer sportId;

		public Integer sportId() {
			return this.sportId;
		}

		private Long leagueId;

		public Long leagueId() {
			return this.leagueId;
		}

		private BigDecimal handicap;

		public BigDecimal handicap() {
			return this.handicap;
		}

		private Optional<String> teamName;

		public Optional<String> teamName() {
			return this.teamName;
		}

		private Optional<SIDE_TYPE> side;

		public Optional<SIDE_TYPE> side() {
			return this.side;
		}

		private String team1;

		public String team1() {
			return this.team1;
		}

		private String team2;

		public String team2() {
			return this.team2;
		}

		private Integer periodNumber;

		public Integer periodNumber() {
			return this.periodNumber;
		}

		private TeaserBetLeg(Json json) {
			this.legBetType = json.getAsString("legBetType").map(LEG_BET_TYPE::fromAPI).orElse(null);
			this.legBetStatus = json.getAsString("legBetStatus").map(LEG_BET_STATUS::fromAPI).orElse(null);
			this.eventId = json.getAsLong("eventId").orElse(null);
			this.eventStartTime = json.getAsString("eventStartTime").map(Instant::parse).orElse(null);
			this.sportId = json.getAsInteger("sportId").orElse(null);
			this.leagueId = json.getAsLong("leagueId").orElse(null);
			this.handicap = json.getAsBigDecimal("handicap").orElse(null);
			this.teamName = json.getAsString("teamName");
			this.side = json.getAsString("side").map(SIDE_TYPE::fromAPI);
			this.team1 = json.getAsString("team1").orElse(null);
			this.team2 = json.getAsString("team2").orElse(null);
			this.periodNumber = json.getAsInteger("periodNumber").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.legBetType == null || this.legBetStatus == null || this.eventId == null
					|| this.eventStartTime == null || this.sportId == null || this.leagueId == null
					|| this.handicap == null || this.team1 == null || this.team2 == null || this.periodNumber == null;
		}

		@Override
		public String toString() {
			return "TeaserBetLeg [legBetType=" + legBetType + ", legBetStatus=" + legBetStatus + ", eventId=" + eventId
					+ ", eventStartTime=" + eventStartTime + ", sportId=" + sportId + ", leagueId=" + leagueId
					+ ", handicap=" + handicap + ", teamName=" + teamName + ", side=" + side + ", team1=" + team1
					+ ", team2=" + team2 + ", periodNumber=" + periodNumber + "]";
		}
	}

	public static class SpecialBet extends AbstractDataObject {

		private Long betId;

		public Long betId() {
			return this.betId;
		}

		private Integer wagerNumber;

		public Integer wagerNumber() {
			return this.wagerNumber;
		}

		private Instant placedAt;

		public Instant placedAt() {
			return this.placedAt;
		}

		private BET_STATUS betStatus;

		public BET_STATUS betStatus() {
			return this.betStatus;
		}

		private BETLIST_BET_TYPE betType;

		public BETLIST_BET_TYPE betType() {
			return this.betType;
		}

		private BigDecimal win;

		public BigDecimal win() {
			return this.win;
		}

		private BigDecimal risk;

		public BigDecimal risk() {
			return this.risk;
		}

		private BigDecimal winLoss;

		public BigDecimal winLoss() {
			return this.winLoss;
		}

		private ODDS_FORMAT oddsFormat;

		public ODDS_FORMAT oddsFormat() {
			return this.oddsFormat;
		}

		private Optional<BigDecimal> customerCommission;

		public Optional<BigDecimal> customerCommission() {
			return this.customerCommission;
		}

		private Long specialId;

		public Long specialId() {
			return this.specialId;
		}

		private Long contestantId;

		public Long contestantId() {
			return this.contestantId;
		}

		private String contestantName;

		public String contestantName() {
			return this.contestantName;
		}

		private BigDecimal price;

		public BigDecimal price() {
			return this.price;
		}

		private Optional<BigDecimal> handicap;

		public Optional<BigDecimal> handicap() {
			return this.handicap;
		}

		private Integer sportId;

		public Integer sportId() {
			return this.sportId;
		}

		private Long leagueId;

		public Long leagueId() {
			return this.leagueId;
		}

		private Optional<Long> eventId;

		public Optional<Long> eventId() {
			return this.eventId;
		}

		private Optional<Integer> periodNumber;

		public Optional<Integer> periodNumber() {
			return this.periodNumber;
		}

		private Optional<CancellationReason> cancellationReason;

		public Optional<CancellationReason> cancellationReason() {
			return this.cancellationReason;
		}

		private SpecialBet(Json json) {
			this.betId = json.getAsLong("betId").orElse(null);
			this.wagerNumber = json.getAsInteger("wagerNumber").orElse(null);
			this.placedAt = json.getAsString("placedAt").map(s -> s + "Z").map(Instant::parse).orElse(null);
			this.betStatus = json.getAsString("betStatus").map(BET_STATUS::fromAPI).orElse(null);
			this.betType = json.getAsString("betType").map(BETLIST_BET_TYPE::fromAPI).orElse(null);
			this.win = json.getAsBigDecimal("win").orElse(null);
			this.risk = json.getAsBigDecimal("risk").orElse(null);
			this.winLoss = json.getAsBigDecimal("winLoss").orElse(null);
			this.oddsFormat = json.getAsString("oddsFormat").map(ODDS_FORMAT::fromAPI).orElse(null);
			this.customerCommission = json.getAsBigDecimal("customerCommission");
			this.specialId = json.getAsLong("specialId").orElse(null);
			this.contestantId = json.getAsLong("contestantId").orElse(null);
			this.contestantName = json.getAsString("contestantName").orElse(null);
			this.price = json.getAsBigDecimal("price").orElse(null);
			this.handicap = json.getAsBigDecimal("handicap");
			this.sportId = json.getAsInteger("sportId").orElse(null);
			this.leagueId = json.getAsLong("leagueId").orElse(null);
			this.eventId = json.getAsLong("eventId");
			this.periodNumber = json.getAsInteger("periodNumber");
			this.cancellationReason = json.getAsJson("cancellationReason").map(CancellationReason::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.betId == null || this.wagerNumber == null || this.placedAt == null || this.betStatus == null
					|| this.betType == null || this.win == null || this.risk == null || this.winLoss == null
					|| this.oddsFormat == null || this.specialId == null || this.contestantId == null
					|| this.contestantName == null || this.price == null || this.sportId == null
					|| this.leagueId == null;
		}

		@Override
		public String toString() {
			return "SpecialBet [betId=" + betId + ", wagerNumber=" + wagerNumber + ", placedAt=" + placedAt
					+ ", betStatus=" + betStatus + ", betType=" + betType + ", win=" + win + ", risk=" + risk
					+ ", winLoss=" + winLoss + ", oddsFormat=" + oddsFormat + ", customerCommission="
					+ customerCommission + ", specialId=" + specialId + ", contestantId=" + contestantId
					+ ", contestantName=" + contestantName + ", price=" + price + ", handicap=" + handicap
					+ ", sportId=" + sportId + ", leagueId=" + leagueId + ", eventId=" + eventId + ", periodNumber="
					+ periodNumber + ", cancellationReason=" + cancellationReason + "]";
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
