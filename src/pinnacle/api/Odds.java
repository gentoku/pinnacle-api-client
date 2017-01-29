package pinnacle.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Odds {
	
	/**
	 * Same as requested sport Id.
	 */
	private Integer sportId;
	public Integer sportId () { return this.sportId; }
	
	/**
	 * Use this value for the subsequent requests for since query parameter 
	 * to get just the changes since previous response.
	 */
	private Long last;
	public Long last () { return this.last; }
	
	/**
	 * List of Leagues.
	 */
	private List<League> leagues = new ArrayList<>();
	public List<League> leagues () { return this.leagues; }
	
	public static class League {

		/**
		 * League ID.
		 */
		private Integer id;
		public Integer id () { return this.id; }
		
		/**
		 * List of events.
		 */
		private List<Event> events = new ArrayList<>();
		public List<Event> events () { return this.events; }
		
		private League (Json json) {
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.League.id"));
			this.events = json.getAsJsonStream("events")
					.map(Event::parse)
					.collect(Collectors.toList());
		}
		
		static League parse (Json json) {
			return new League(json);
		}

		@Override
		public String toString() {
			return "League [id=" + id + ", events=" + events + "]";
		}
		
	}
	
	public static class Event {
		
		/**
		 * Event ID.
		 */
		private Integer id;
		public Integer id () { return this.id; }
		
		/**
		 * List of periods.
		 */
		private List<Period> periods = new ArrayList<>();
		public List<Period> periods () { return this.periods; }
		
		/**
		 * Home team score. Only for live events. (Optional)
		 */
		private Optional<BigDecimal> homeScore;
		public Optional<BigDecimal> homeScore () { return this.homeScore; }
		
		/**
		 * Away team score. Only for live events. (Optional)
		 */
		private Optional<BigDecimal> awayScore;
		public Optional<BigDecimal> awayScore () { return this.awayScore; }
		
		/**
		 * Home team red cards. Only for live events. (Optional)
		 */
		private Optional<Integer> homeRedCards;
		public Optional<Integer> homeRedCards () { return this.homeRedCards; }
		
		/**
		 * Away team red cards. Only for live events. (Optional)
		 */
		private Optional<Integer> awayRedCards;
		public Optional<Integer> awayRedCards () { return this.awayRedCards; }
		
		private Event (Json json) {
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Event.id"));
			this.periods = json.getAsJsonStream("periods")
					.map(Period::parse)
					.collect(Collectors.toList());
			this.homeScore = json.getAsBigDecimal("homeScore"); // optional
			this.awayScore = json.getAsBigDecimal("awayScore"); // optional
			this.homeRedCards = json.getAsInteger("homeRedCards"); // optional
			this.awayRedCards = json.getAsInteger("awayRedCards"); // optional
		}
		
		static Event parse (Json json) {
			return new Event(json);
		}

		@Override
		public String toString() {
			return "Event [id=" + id + ", periods=" + periods + ", homeScore="
					+ homeScore + ", awayScore=" + awayScore
					+ ", homeRedCards=" + homeRedCards + ", awayRedCards="
					+ awayRedCards + "]";
		}
		
	} 
	
	public static class Period {
		
		/**
		 * Line ID.
		 */
		private Integer lineId;
		public Integer lineId () { return this.lineId; }
		
		/**
		 * This represents the period of the match. For example, for soccer we have:
		 * 	0 - Game
		 * 	1 - 1st Half
		 * 	2 - 2nd Half
		 * Full list of periods per sports at 
		 * 	http://www.pinnaclesports.com/en/api/manual#Gsports
		 */
		private Integer number;
		public Integer number () { return this.number; }
		
		/**
		 * Period’s wagering cut-off date.
		 */
		private Instant cutoff;
		public Instant cutoff () { return this.cutoff; }
		
		/**
		 * Odds of money line or 1x2 match bet. (Optional)
		 */
		private Optional<MoneyLine> moneyLine;
		public Optional<MoneyLine> moneyLine () { return this.moneyLine; }
		
		/**
		 * List of odds of spread or Asian handicap. (Optional)
		 */
		private List<Spread> spreads = new ArrayList<>(); // size zero list if not exists.
		public List<Spread> spreads () { return this.spreads; }
		
		/**
		 * List of odds of total points or Under/Over of a match. (Optional)
		 */
		private List<TotalPoints> totals = new ArrayList<>(); // size zero list if not exists.
		public List<TotalPoints> totals () { return this.totals; }
		
		/**
		 * Odds of team total points or Under/Over of a team. (Optional)
		 */
		private Optional<TeamTotalPoints> teamTotals;
		public Optional<TeamTotalPoints> teamTotals () { return this.teamTotals; }
		
		/**
		 * Maximum moneyline bet. Only in straight odds response. (Optional)
		 */
		private Optional<BigDecimal> maxMoneyLine;
		public Optional<BigDecimal> maxMoneyLine () { return this.maxMoneyLine; }
		
		/**
		 * Maximum spread bet. Only in straight odds response. (Optional)
		 */
		private Optional<BigDecimal> maxSpread;
		public Optional<BigDecimal> maxSpread () { return this.maxSpread; }
		
		/**
		 * Maximum total points bet. Only in straight odds response. (Optional)
		 */
		private Optional<BigDecimal> maxTotal;
		public Optional<BigDecimal> maxTotal () { return this.maxTotal; }
		
		/**
		 * Maximum team total points bet. Only in straight odds response. (Optional)
		 */
		private Optional<BigDecimal> maxTeamTotal;
		public Optional<BigDecimal> maxTeamTotal () { return this.maxTeamTotal; }
		
		private Period (Json json) {
			this.lineId = json.getAsInteger("lineId")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Period.lineId"));
			this.number = json.getAsInteger("number")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Period.number"));
			this.cutoff = json.getAsString("cutoff")
					.map(Instant::parse)
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Period.cutoff"));
			this.moneyLine = json.getAsJson("moneyLine")
					.map(MoneyLine::parse); // optional
			this.spreads = json.getAsJsonStream("spreads")
					.map(Spread::parse)
					.collect(Collectors.toList());
			this.totals = json.getAsJsonStream("totals")
					.map(TotalPoints::parse)
					.collect(Collectors.toList());
			this.teamTotals = json.getAsJson("teamTotals")
					.map(TeamTotalPoints::parse); // optional
			this.maxMoneyLine = json.getAsBigDecimal("maxMoneyLine"); // optional
			this.maxSpread = json.getAsBigDecimal("maxSpread"); // optional
			this.maxTotal = json.getAsBigDecimal("maxTotal"); // optional
			this.maxTeamTotal = json.getAsBigDecimal("maxTeamTotal"); // optional
		}
		
		static Period parse (Json json) {
			return new Period(json);
		}

		@Override
		public String toString() {
			return "Period [lineId=" + lineId + ", number=" + number
					+ ", cutoff=" + cutoff + ", moneyLine=" + moneyLine
					+ ", spreads=" + spreads + ", totals=" + totals
					+ ", teamTotals=" + teamTotals + ", maxMoneyLine="
					+ maxMoneyLine + ", maxSpread=" + maxSpread + ", maxTotal="
					+ maxTotal + ", maxTeamTotal=" + maxTeamTotal + "]";
		}
	} 
	
	public static class MoneyLine {
		
		/**
		 * Home team price.
		 */
		private BigDecimal home;
		public BigDecimal home () { return this.home; }
		
		/**
		 * Away team price.
		 */
		private BigDecimal away;
		public BigDecimal away () { return this.away; }
		
		/**
		 * Draw price. This is present only for events we offer price for draw. (Optional)
		 */
		private BigDecimal draw;
		public BigDecimal draw () { return this.draw; }
		
		private MoneyLine (Json json) {
			this.home = json.getAsBigDecimal("home")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.MoneyLine.home"));
			this.away = json.getAsBigDecimal("away")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.MoneyLine.away"));
			this.draw = json.getAsBigDecimal("draw")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.MoneyLine.draw"));
		}
		
		static MoneyLine parse (Json json) {
			return new MoneyLine(json);
		}

		@Override
		public String toString() {
			return "MoneyLine [home=" + home + ", away=" + away + ", draw="
					+ draw + "]";
		}
		
	}

	public static class Spread {
		
		/**
		 * This is present only if it’s alternative line. (Optional)
		 */
		private Optional<Integer> altLineId;
		public Optional<Integer> altLineId () { return this.altLineId; }
		
		/**
		 * Home team price.
		 */
		private BigDecimal home;
		public BigDecimal home () { return this.home; }
		
		/**
		 * Away team price.
		 */
		private BigDecimal away;
		public BigDecimal away () { return this.away; }
		
		/**
		 * Home team handicap.
		 */
		private BigDecimal hdp;
		public BigDecimal hdp () { return this.hdp; }
		
		private Spread (Json json) {
			this.altLineId = json.getAsInteger("altLineId");
			this.home = json.getAsBigDecimal("home")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Spread.home"));
			this.away = json.getAsBigDecimal("away")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Spread.away"));
			this.hdp = json.getAsBigDecimal("hdp")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.Spread.hdp"));
		}
		
		static Spread parse (Json json) {
			return new Spread(json); 
		}

		@Override
		public String toString() {
			return "Spread [altLineId=" + altLineId + ", home=" + home
					+ ", away=" + away + ", hdp=" + hdp + "]";
		}
		
	}
	
	public static class TotalPoints {
		
		/**
		 * This is present only if it’s alternative line. (Optional)
		 */
		private Optional<Integer> altLineId;
		public Optional<Integer> altLineId () { return this.altLineId; }
		
		/**
		 * Total points.
		 */
		private BigDecimal points;
		public BigDecimal points () { return this.points; }
		
		/**
		 * Over price.
		 */
		private BigDecimal over;
		public BigDecimal over () { return this.over; }
		
		/**
		 * Under price.
		 */
		private BigDecimal under;
		public BigDecimal under () { return this.under; }
		
		private TotalPoints (Json json) {
			this.altLineId = json.getAsInteger("altLineId");
			this.points = json.getAsBigDecimal("points")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.TotalPoints.points"));
			this.over = json.getAsBigDecimal("over")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.TotalPoints.over"));
			this.under = json.getAsBigDecimal("under")
					.orElseThrow(() -> NoNecessaryKeyException.of("Odds.TotalPoints.under"));
		}
		
		static TotalPoints parse (Json json) {
			return new TotalPoints(json);
		}

		@Override
		public String toString() {
			return "TotalPoints [altLineId=" + altLineId + ", points=" + points
					+ ", over=" + over + ", under=" + under + "]";
		}
		
	}
	
	public static class TeamTotalPoints {
		
		/**
		 * Home team total points. (Optional)
		 */
		private Optional<TotalPoints> home;
		public Optional<TotalPoints> home () { return this.home; }
		
		/**
		 * Away team total points. (Optional)
		 */
		private Optional<TotalPoints> away;
		public Optional<TotalPoints> away () { return this.away; }
		
		private TeamTotalPoints (Json json) {
			this.home = json.getAsJson("home").map(TotalPoints::parse);
			this.away = json.getAsJson("away").map(TotalPoints::parse);
		}
		
		static TeamTotalPoints parse (Json json) {
			return new TeamTotalPoints(json);
		}

		@Override
		public String toString() {
			return "TeamTotalPoints [home=" + home + ", away=" + away + "]";
		}
		
	}
	
	private Odds () {}
	
	private Odds (Json json) {
		this.sportId = json.getAsInteger("sportId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Odds.sportId"));
		this.last = json.getAsLong("last")
				.orElseThrow(() -> NoNecessaryKeyException.of("Odds.last"));
		this.leagues = json.getAsJsonStream("leagues")
						.map(League::parse)
						.collect(Collectors.toList());
	}
	
	static Odds parse (String text) throws PinnacleException {
		return text.equals("")
				? new Odds()
				: new Odds(Json.of(text));
	}

	@Override
	public String toString() {
		return "Odds [sportId=" + sportId + ", last=" + last + ", leagues="
				+ leagues + "]";
	}
}
