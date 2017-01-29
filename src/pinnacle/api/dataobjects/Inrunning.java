package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.INRUNNING_STATE;

public class Inrunning extends AbstractDataObject {

	private List<Sport> sports = new ArrayList<>();

	public List<Sport> sports() {
		return this.sports;
	}

	public Stream<Sport> sportsAsStream() {
		return this.sports.stream();
	}

	private Inrunning(Json json) {
		this.sports = json.getAsJsonStream("sports").map(Sport::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Inrunning() {
		this.isEmpty = true;
	}

	public static Inrunning parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Inrunning() : new Inrunning(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Inrunning [sports=" + sports + "]";
	}

	public static class Sport extends AbstractDataObject {

		private Long id;

		public Long id() {
			return this.id;
		}

		private List<League> leagues = new ArrayList<>();

		public List<League> leagues() {
			return this.leagues;
		}

		public Stream<League> leaguesAsStream() {
			return this.leagues.stream();
		}

		private Sport(Json json) {
			this.id = json.getAsLong("id").orElse(null);
			this.leagues = json.getAsJsonStream("leagues").map(League::new).collect(Collectors.toList());
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.id == null;
		}

		@Override
		public String toString() {
			return "Sport [id=" + id + ", leagues=" + leagues + "]";
		}
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

		private Integer elapsed;

		public Integer elapsed() {
			return this.elapsed;
		}

		private INRUNNING_STATE state;

		public INRUNNING_STATE state() {
			return this.state;
		}

		private Long id;

		public Long id() {
			return this.id;
		}

		private Event(Json json) {
			this.elapsed = json.getAsInteger("elapsed").orElse(null);
			this.state = json.getAsString("state").map(INRUNNING_STATE::fromAPI).orElse(null);
			this.id = json.getAsLong("id").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.elapsed == null || this.state == null || this.id == null;
		}

		@Override
		public String toString() {
			return "Event [elapsed=" + elapsed + ", state=" + state + ", id=" + id + "]";
		}
	}
}
