package pinnacle.api;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Enums.EVENT_STATUS;
import pinnacle.api.Enums.LIVE_STATUS;
import pinnacle.api.Enums.PARLAY_RESTRICTION;

/**
 * 
 * @author gentoku@gmail.com
 *
 */
public class Fixture {

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
	 * League ID.
	 */
	private Integer leagueId;
	public Integer leagueId () { return this.leagueId; }
	
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

	private Fixture (Fixtures.Event event) {
		this.id = event.id();
		this.starts = event.starts();
		this.home = event.home();
		this.away = event.away();
		this.rotNum = event.rotNum();
		this.liveStatus = event.liveStatus();
		this.status = event.status();
		this.parlayRestriction = event.parlayRestriction();
	}
	
	static List<Fixture> toList (Fixtures fixtures) {
		Integer sportId = fixtures.sportId();
		Long last = fixtures.last();
		return fixtures.leagues()
				.parallelStream()
				.flatMap(Fixture::getEvents)
				.peek(fixture -> fixture.sportId = sportId)
				.peek(fixture -> fixture.last = last)
				.collect(Collectors.toList());
	}
	
	private static Stream<Fixture> getEvents (Fixtures.League league) {
		Integer leagueId = league.id();
		return league.events()
				.parallelStream()
				.map(Fixture::new)
				.peek(fixture -> fixture.leagueId = leagueId);
	}

	@Override
	public String toString() {
		return "Fixture [sportId=" + sportId + ", last=" + last + ", leagueId="
				+ leagueId + ", id=" + id + ", starts=" + starts + ", home="
				+ home + ", away=" + away + ", rotNum=" + rotNum
				+ ", liveStatus=" + liveStatus + ", status=" + status
				+ ", parlayRestriction=" + parlayRestriction + "]";
	}
	
}

