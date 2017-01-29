package pinnacle.api;

import java.time.Instant;
import java.util.List;

import pinnacle.api.Enums.EVENT_STATUS;
import pinnacle.api.Enums.LIVE_STATUS;

@Deprecated
public class Feed {

	/**
	 * 
	 */
	private Long fdTime;
	public Long fdTime () {
		return this.fdTime;
	}

	/**
	 * 
	 */
	private List<FeedSport> feedSports;
	public List<FeedSport> feedSports () {
		return this.feedSports;
	}
	
	public class FeedSport {

		/**
		 * 
		 */
		private Integer id;
		public Integer id () {
			return this.id;
		}

		/**
		 * 
		 */
		private List<FeedLeague> feedLeagues;
		public List<FeedLeague> feedLeagues () {
			return this.feedLeagues;
		}
		
	}
	
	public class FeedLeague {

		/**
		 * 
		 */
		private Integer id;
		public Integer id () {
			return this.id;
		}

		/**
		 * 
		 */
		private List<FeedEvent> feedEvents;
		public List<FeedEvent> feedEvents () {
			return this.feedEvents;
		}
	}
	
	public class FeedEvent {

		/**
		 * 
		 */
		private Integer id;
		public Integer id () {
			return this.id;
		}

		/**
		 * 
		 */
		private Instant startDateTime;
		public Instant startDateTime () {
			return this.startDateTime;
		}

		/**
		 * 
		 */
		private boolean isLive;
		public boolean isLive () {
			return this.isLive;
		}

		/**
		 * 
		 */
		private LIVE_STATUS lStatus;
		public LIVE_STATUS lStatus () {
			return this.lStatus;
		}

		/**
		 * 
		 */
		private EVENT_STATUS status;
		public EVENT_STATUS status () {
			return this.status;
		}

		/**
		 * 
		 */
		private Integer drawRotNum;
		public Integer drawRotNum () {
			return this.drawRotNum;
		}

		/**
		 * 
		 */
		private FeedTeam awayTeam;
		public FeedTeam awayTeam () {
			return this.awayTeam;
		};

		/**
		 * 
		 */
		private FeedTeam homeTeam;
		public FeedTeam homeTeam () {
			return this.homeTeam;
		};

		/**
		 * 
		 */
		private List<FeedPeriod> periods;
		public List<FeedPeriod> periods () {
			return this.periods;
		}
		
	}
	
	public class FeedTeam {

		/**
		 * 
		 */
		private String name;
		public String name () {
			return this.name;
		}

		/**
		 * 
		 */
		private Integer rotNum;
		public Integer rotNum () {
			return this.rotNum;
		}

		/**
		 * 
		 */
		private String pitcher;
		public String pitcher () {
			return this.pitcher;
		}

		/**
		 * 
		 */
		private Integer score;
		public Integer score () {
			return this.score;
		}

		/**
		 * 
		 */
		private Integer redCards;
		public Integer redCards () {
			return this.redCards;
		}
	}
	
	public class FeedPeriod {
		
	}
}
