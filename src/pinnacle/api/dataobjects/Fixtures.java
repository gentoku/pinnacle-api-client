package pinnacle.api.dataobjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.EVENT_STATUS;
import pinnacle.api.enums.LIVE_STATUS;
import pinnacle.api.enums.PARLAY_RESTRICTION;

public class Fixtures extends AbstractDataObject {

	private Integer sportId;

	public Integer sportId() {
		return this.sportId;
	}

	private Long last;

	public Long last() {
		return this.last;
	}

	private List<League> league = new ArrayList<>();

	public List<League> league() {
		return this.league;
	}

	public Stream<League> leagueAsStream() {
		return this.league.stream();
	}

	private Fixtures(Json json) {
		this.sportId = json.getAsInteger("sportId").orElse(null);
		this.last = json.getAsLong("last").orElse(null);
		this.league = json.getAsJsonStream("league").map(League::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Fixtures() {
		this.isEmpty = true;
	}

	public static Fixtures parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Fixtures() : new Fixtures(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.sportId == null || this.last == null;
	}

	@Override
	public String toString() {
		return "Fixtures [sportId=" + sportId + ", last=" + last + ", league=" + league + "]";
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

		private Instant starts;

		public Instant starts() {
			return this.starts;
		}

		private String home;

		public String home() {
			return this.home;
		}

		private String away;

		public String away() {
			return this.away;
		}

		private String rotNum;

		public String rotNum() {
			return this.rotNum;
		}

		private Optional<LIVE_STATUS> liveStatus;

		public Optional<LIVE_STATUS> liveStatus() {
			return this.liveStatus;
		}

		private EVENT_STATUS status;

		public EVENT_STATUS status() {
			return this.status;
		}

		private PARLAY_RESTRICTION parlayRestriction;

		public PARLAY_RESTRICTION parlayRestriction() {
			return this.parlayRestriction;
		}

		private Optional<String> homePitcher;

		public Optional<String> homePitcher() {
			return this.homePitcher;
		}

		private Optional<String> awayPitcher;

		public Optional<String> awayPitcher() {
			return this.awayPitcher;
		}

		private Event(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.starts = json.getAsString("starts").map(Instant::parse).orElse(null);
			this.home = json.getAsString("home").orElse(null);
			this.away = json.getAsString("away").orElse(null);
			this.rotNum = json.getAsString("rotNum").orElse(null);
			this.liveStatus = json.getAsString("liveStatus").map(LIVE_STATUS::fromAPI);
			this.status = json.getAsString("status").map(EVENT_STATUS::fromAPI).orElse(null);
			this.parlayRestriction = json.getAsString("parlayRestriction").map(PARLAY_RESTRICTION::fromAPI)
					.orElse(null);
			this.homePitcher = json.getAsString("homePitcher");
			this.awayPitcher = json.getAsString("awayPitcher");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null || this.starts == null || this.home == null || this.away == null
					|| this.rotNum == null || this.status == null || this.parlayRestriction == null;
		}

		@Override
		public String toString() {
			return "Event [id=" + id + ", starts=" + starts + ", home=" + home + ", away=" + away + ", rotNum=" + rotNum
					+ ", liveStatus=" + liveStatus + ", status=" + status + ", parlayRestriction=" + parlayRestriction
					+ ", homePitcher=" + homePitcher + ", awayPitcher=" + awayPitcher + "]";
		}
	}
}
