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

public class Odds extends AbstractDataObject {

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

	private Odds(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Odds() {
		this.isEmpty = true;
	}

	public static Odds parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Odds() : new Odds(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "Odds [sportId=" + sportId + ", last=" + last + ", leagues=" + leagues + "]";
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

		private Optional<BigDecimal> awayScore;

		public Optional<BigDecimal> awayScore() {
			return this.awayScore;
		}

		private Optional<BigDecimal> homeScore;

		public Optional<BigDecimal> homeScore() {
			return this.homeScore;
		}

		private Optional<Integer> awayRedCards;

		public Optional<Integer> awayRedCards() {
			return this.awayRedCards;
		}

		private Optional<Integer> homeRedCards;

		public Optional<Integer> homeRedCards() {
			return this.homeRedCards;
		}

		private Event(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.periods = json.getAsJsonStream("periods").map(Period::new).collect(Collectors.toList());
			this.awayScore = json.getAsBigDecimal("awayScore");
			this.homeScore = json.getAsBigDecimal("homeScore");
			this.awayRedCards = json.getAsInteger("awayRedCards");
			this.homeRedCards = json.getAsInteger("homeRedCards");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "Event [id=" + id + ", periods=" + periods + ", awayScore=" + awayScore + ", homeScore=" + homeScore
					+ ", awayRedCards=" + awayRedCards + ", homeRedCards=" + homeRedCards + "]";
		}
	}

	public static class Period extends AbstractDataObject {

		private Long lineId;

		public Long lineId() {
			return this.lineId;
		}

		private Integer number;

		public Integer number() {
			return this.number;
		}

		private Instant cutoff;

		public Instant cutoff() {
			return this.cutoff;
		}

		private List<Spread> spreads = new ArrayList<>();

		public List<Spread> spreads() {
			return this.spreads;
		}

		public Stream<Spread> spreadsAsStream() {
			return this.spreads.stream();
		}

		private List<TotalPoints> totals = new ArrayList<>();

		public List<TotalPoints> totals() {
			return this.totals;
		}

		public Stream<TotalPoints> totalsAsStream() {
			return this.totals.stream();
		}

		private Optional<MoneyLine> moneyline;

		public Optional<MoneyLine> moneyline() {
			return this.moneyline;
		}

		private Optional<TeamTotalPoints> teamTotal;

		public Optional<TeamTotalPoints> teamTotal() {
			return this.teamTotal;
		}

		private Optional<BigDecimal> maxSpread;

		public Optional<BigDecimal> maxSpread() {
			return this.maxSpread;
		}

		private Optional<BigDecimal> maxTotal;

		public Optional<BigDecimal> maxTotal() {
			return this.maxTotal;
		}

		private Optional<BigDecimal> maxMoneyline;

		public Optional<BigDecimal> maxMoneyline() {
			return this.maxMoneyline;
		}

		private Optional<BigDecimal> maxTeamTotal;

		public Optional<BigDecimal> maxTeamTotal() {
			return this.maxTeamTotal;
		}

		private Period(Json json) {
			this.lineId = json.getAsLong("lineId").orElse(null);
			this.number = json.getAsInteger("number").orElse(null);
			this.cutoff = json.getAsString("cutoff").map(Instant::parse).orElse(null);
			this.spreads = json.getAsJsonStream("spreads").map(Spread::new).collect(Collectors.toList());
			this.totals = json.getAsJsonStream("totals").map(TotalPoints::new).collect(Collectors.toList());
			this.moneyline = json.getAsJson("moneyline").map(MoneyLine::new);
			this.teamTotal = json.getAsJson("teamTotal").map(TeamTotalPoints::new);
			this.maxSpread = json.getAsBigDecimal("maxSpread");
			this.maxTotal = json.getAsBigDecimal("maxTotal");
			this.maxMoneyline = json.getAsBigDecimal("maxMoneyline");
			this.maxTeamTotal = json.getAsBigDecimal("maxTeamTotal");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.lineId == null || this.number == null || this.cutoff == null;
		}

		@Override
		public String toString() {
			return "Period [lineId=" + lineId + ", number=" + number + ", cutoff=" + cutoff + ", spreads=" + spreads
					+ ", totals=" + totals + ", moneyline=" + moneyline + ", teamTotal=" + teamTotal + ", maxSpread="
					+ maxSpread + ", maxTotal=" + maxTotal + ", maxMoneyline=" + maxMoneyline + ", maxTeamTotal="
					+ maxTeamTotal + "]";
		}
	}

	public static class Spread extends AbstractDataObject {

		private Optional<Long> altLineId;

		public Optional<Long> altLineId() {
			return this.altLineId;
		}

		private BigDecimal hdp;

		public BigDecimal hdp() {
			return this.hdp;
		}

		private BigDecimal away;

		public BigDecimal away() {
			return this.away;
		}

		private BigDecimal home;

		public BigDecimal home() {
			return this.home;
		}

		private Spread(Json json) {
			this.altLineId = json.getAsLong("altLineId");
			this.hdp = json.getAsBigDecimal("hdp").orElse(null);
			this.away = json.getAsBigDecimal("away").orElse(null);
			this.home = json.getAsBigDecimal("home").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.hdp == null || this.away == null || this.home == null;
		}

		@Override
		public String toString() {
			return "Spread [altLineId=" + altLineId + ", hdp=" + hdp + ", away=" + away + ", home=" + home + "]";
		}
	}

	public static class MoneyLine extends AbstractDataObject {

		private BigDecimal away;

		public BigDecimal away() {
			return this.away;
		}

		private BigDecimal home;

		public BigDecimal home() {
			return this.home;
		}

		private Optional<BigDecimal> draw;

		public Optional<BigDecimal> draw() {
			return this.draw;
		}

		private MoneyLine(Json json) {
			this.away = json.getAsBigDecimal("away").orElse(null);
			this.home = json.getAsBigDecimal("home").orElse(null);
			this.draw = json.getAsBigDecimal("draw");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.away == null || this.home == null;
		}

		@Override
		public String toString() {
			return "MoneyLine [away=" + away + ", home=" + home + ", draw=" + draw + "]";
		}
	}

	public static class TotalPoints extends AbstractDataObject {

		private Optional<Long> altLineId;

		public Optional<Long> altLineId() {
			return this.altLineId;
		}

		private BigDecimal points;

		public BigDecimal points() {
			return this.points;
		}

		private BigDecimal over;

		public BigDecimal over() {
			return this.over;
		}

		private BigDecimal under;

		public BigDecimal under() {
			return this.under;
		}

		private TotalPoints(Json json) {
			this.altLineId = json.getAsLong("altLineId");
			this.points = json.getAsBigDecimal("points").orElse(null);
			this.over = json.getAsBigDecimal("over").orElse(null);
			this.under = json.getAsBigDecimal("under").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.points == null || this.over == null || this.under == null;
		}

		@Override
		public String toString() {
			return "TotalPoints [altLineId=" + altLineId + ", points=" + points + ", over=" + over + ", under=" + under
					+ "]";
		}
	}

	public static class TeamTotalPoints extends AbstractDataObject {

		private Optional<TotalPoints> away;

		public Optional<TotalPoints> away() {
			return this.away;
		}

		private Optional<TotalPoints> home;

		public Optional<TotalPoints> home() {
			return this.home;
		}

		private TeamTotalPoints(Json json) {
			this.away = json.getAsJson("away").map(TotalPoints::new);
			this.home = json.getAsJson("home").map(TotalPoints::new);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return false;
		}

		@Override
		public String toString() {
			return "TeamTotalPoints [away=" + away + ", home=" + home + "]";
		}
	}
}
