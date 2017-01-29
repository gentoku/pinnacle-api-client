package pinnacle.api;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.Enums.EVENT_STATUS;
import pinnacle.api.Enums.LIVE_STATUS;
import pinnacle.api.Enums.PARLAY_RESTRICTION;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Fixtures {

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
	 * List of leagues.
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
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.League.id"));
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
		 * Start time of the event in UTC.
		 */
		private Instant starts;
		public Instant starts () { return this.starts; } 
		
		/**
		 * Home team name.
		 */
		private String home;
		public String home () { return this.home; }
		
		/**
		 * Away team name.
		 */
		private String away;
		public String away () { return this.away; }
		
		/**
		 * Team1 rotation number.
		 */	
		private String rotNum;
		public String rotNum () { return this.rotNum; }
		
		/**
		 * Indicates event’s live status. (Optional)
		 */
		private Optional<LIVE_STATUS> liveStatus;
		public Optional<LIVE_STATUS> liveStatus () { return this.liveStatus; }
		
		/**
		 * Status of the event.
		 */
		private EVENT_STATUS status;
		public EVENT_STATUS status () { return this.status; }
		
		/**
		 * Parlay status of the event.
		 */
		private PARLAY_RESTRICTION parlayRestriction;
		public PARLAY_RESTRICTION parlayRestriction () { return this.parlayRestriction; }
		
		Event () {};
		
		private Event (Json json) {
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.Event.id"));
			this.starts = json.getAsString("starts")
					.map(Instant::parse)
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.Event.starts"));
			this.home = json.getAsString("home")
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.Event.home"));
			this.away = json.getAsString("away")
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.Event.away"));
			this.rotNum = json.getAsString("rotNum")
					.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.Event.rotNum"));
			this.liveStatus = json.getAsString("liveStatus")
					.map(LIVE_STATUS::of); // Optional
			this.status = json.getAsString("status")
					.map(EVENT_STATUS::valueOf)
					.orElseThrow(() -> NoNecessaryKeyException.of("status"));
			this.parlayRestriction = json.getAsString("parlayRestriction")
					.map(PARLAY_RESTRICTION::of)
					.orElseThrow(() -> NoNecessaryKeyException.of("parlayRestriction"));
		}
		
		static Event parse (Json json) {
			return new Event(json);
		}

		@Override
		public String toString() {
			return "Event [id=" + id + ", starts=" + starts + ", home=" + home
					+ ", away=" + away + ", rotNum=" + rotNum + ", liveStatus="
					+ liveStatus + ", status=" + status
					+ ", parlayRestriction=" + parlayRestriction + "]";
		}
		
	}
	
	private Fixtures () {}
	
	private Fixtures (Json json) {
		this.sportId = json.getAsInteger("sportId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.sportId"));
		this.last = json.getAsLong("last")
				.orElseThrow(() -> NoNecessaryKeyException.of("Fixture.last"));
		this.leagues = json.getAsJsonStream("league")
				.map(League::parse)
				.collect(Collectors.toList());
	}
	
	static Fixtures parse (String text) throws PinnacleException {
		return text.equals("")
				? new Fixtures()
				: new Fixtures(Json.of(text));
	}
	
	@Override
	public String toString() {
		return "Fixture [sportId=" + sportId + ", last=" + last + ", leagues="
				+ leagues + "]";
	}
}
