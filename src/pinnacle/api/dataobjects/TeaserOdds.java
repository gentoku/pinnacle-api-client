package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class TeaserOdds extends AbstractDataObject {

	private Long teaserId;

	public Long teaserId() {
		return this.teaserId;
	}

	private Integer sportId;

	public Integer sportId() {
		return this.sportId;
	}

	private List<League> leagues = new ArrayList<>();

	public List<League> leagues() {
		return this.leagues;
	}

	public Stream<League> leaguesAsStream() {
		return this.leagues.stream();
	}

	private TeaserOdds(Json json) {
		this.teaserId = json.getAsLong("teaserId").orElse(null);
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private TeaserOdds() {
		this.isEmpty = true;
	}

	public static TeaserOdds parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new TeaserOdds() : new TeaserOdds(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.teaserId == null || this.sportId == null;
	}

	@Override
	public String toString() {
		return "TeaserOdds [teaserId=" + teaserId + ", sportId=" + sportId + ", leagues=" + leagues + "]";
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

		private Integer number;

		public Integer number() {
			return this.number;
		}

		private Long lineId;

		public Long lineId() {
			return this.lineId;
		}

		private Spread spread;

		public Spread spread() {
			return this.spread;
		}

		private Total total;

		public Total total() {
			return this.total;
		}

		private Period(Json json) {
			this.number = json.getAsInteger("number").orElse(null);
			this.lineId = json.getAsLong("lineId").orElse(null);
			this.spread = json.getAsJson("spread").map(Spread::new).orElse(null);
			this.total = json.getAsJson("total").map(Total::new).orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.number == null || this.lineId == null || this.spread == null || this.total == null;
		}

		@Override
		public String toString() {
			return "Period [number=" + number + ", lineId=" + lineId + ", spread=" + spread + ", total=" + total + "]";
		}
	}

	public static class Spread extends AbstractDataObject {

		private BigDecimal maxBet;

		public BigDecimal maxBet() {
			return this.maxBet;
		}

		private BigDecimal homeHdp;

		public BigDecimal homeHdp() {
			return this.homeHdp;
		}

		private BigDecimal awayHdp;

		public BigDecimal awayHdp() {
			return this.awayHdp;
		}

		private Spread(Json json) {
			this.maxBet = json.getAsBigDecimal("maxBet").orElse(null);
			this.homeHdp = json.getAsBigDecimal("homeHdp").orElse(null);
			this.awayHdp = json.getAsBigDecimal("awayHdp").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.maxBet == null || this.homeHdp == null || this.awayHdp == null;
		}

		@Override
		public String toString() {
			return "Spread [maxBet=" + maxBet + ", homeHdp=" + homeHdp + ", awayHdp=" + awayHdp + "]";
		}
	}

	public static class Total extends AbstractDataObject {

		private BigDecimal maxBet;

		public BigDecimal maxBet() {
			return this.maxBet;
		}

		private BigDecimal overPoints;

		public BigDecimal overPoints() {
			return this.overPoints;
		}

		private BigDecimal underPoints;

		public BigDecimal underPoints() {
			return this.underPoints;
		}

		private Total(Json json) {
			this.maxBet = json.getAsBigDecimal("maxBet").orElse(null);
			this.overPoints = json.getAsBigDecimal("overPoints").orElse(null);
			this.underPoints = json.getAsBigDecimal("underPoints").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.maxBet == null || this.overPoints == null || this.underPoints == null;
		}

		@Override
		public String toString() {
			return "Total [maxBet=" + maxBet + ", overPoints=" + overPoints + ", underPoints=" + underPoints + "]";
		}
	}
}
