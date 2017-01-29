package pinnacle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Enums.INRUNNING_STATE;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

/**
 * A class representing a response by 'get in-running' operation.
 *  
 * @author gentoku@gmail.com
 *
 */
public class InRunning {

	/**
	 * List of sports
	 */
	private List<Sport> sports = new ArrayList<>();
	public List<Sport> sports () { return this.sports; }; 
	
	public static class Sport {
		
		/**
		 * Sport ID.  
		 */
		private Integer id;
		public Integer id () { return this.id; } 

		/**
		 * List of leagues
		 */
		private List<League> leagues = new ArrayList<>();
		public List<League> leagues () { return this.leagues; }; 
		public Stream<League> streamOfLeagues () { return this.leagues.stream(); };
		
		private Sport (Json json) {
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("InRunning.Sport.id"));
			this.leagues = json.getAsJsonStream("leagues")
					.map(League::new)
					.collect(Collectors.toList());
		}
		
		static Sport parse (Json json) {
			return new Sport(json);
		}

		@Override
		public String toString() {
			return "Sport [id=" + id + ", leagues=" + leagues + "]";
		}
		
	}
	
	public static class League {
		
		/**
		 * League ID.  
		 */
		private Integer id;
		public Integer id () { return this.id; } 

		/**
		 * List of leagues
		 */
		private List<Event> events = new ArrayList<>();
		public List<Event> events () { return this.events; };
		public Stream<Event> streamOfEvents () { return this.events.stream(); }
		
		private League (Json json) {
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("InRunning.League.id"));
			this.events = json.getAsJsonStream("events")
					.map(Event::new)
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
		 * Minutes elapsed since start of the state . 
		 * Please note that this will be 0 when the match is in half time.
		 */
		private Integer elapsed;
		public Integer elapsed () { return this.elapsed; }

		/**
		 * The state of the live event.
		 */
		private INRUNNING_STATE state;
		public INRUNNING_STATE state () { return this.state; }
		
		/**
		 * Event ID.
		 */
		private Integer id;
		public Integer id () { return this.id; }
		
		private Event (Json json) {
			this.elapsed = json.getAsInteger("elapsed")
					.orElseThrow(() -> NoNecessaryKeyException.of("InRunning.elapsed"));
			this.state = json.getAsString("state")
					.map(INRUNNING_STATE::of)
					.orElseThrow(() -> NoNecessaryKeyException.of("InRunning.state"));
			this.id = json.getAsInteger("id")
					.orElseThrow(() -> NoNecessaryKeyException.of("InRunning.id"));
		}
		
		static Event parse (Json json) {
			return new Event(json);
		}

		@Override
		public String toString() {
			return "Event [elapsed=" + elapsed + ", state=" + state
					+ ", id=" + id + "]";
		}
		
	}
	
	private InRunning () {}
	
	private InRunning (Json json) {
		this.sports = json.getAsJsonStream("sports")
				.map(Sport::new)
				.collect(Collectors.toList());
	}
	
	static InRunning parse (String text) throws PinnacleException {
		return text.equals("")
				? new InRunning()
				: new InRunning(Json.of(text));
	}

	@Override
	public String toString() {
		return "InRunning [sports=" + sports + "]";
	}
}
