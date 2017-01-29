package pinnacle.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.Enums.BET_STATUS;
import pinnacle.api.Enums.BET_TYPE;
import pinnacle.api.Enums.ODDS_FORMAT;
import pinnacle.api.Enums.SIDE_TYPE;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

/**
 * A class representing Bets a user have bet. 
 * @author gentoku@gmail.com
 *
 */
public class Bet {

	// TODO
	//private List<Leg> legs;
	// waiting for official document about this.
	
	/**
	 * Bet identification.
	 */
	private Integer betId;
	public Integer betId () { return this.betId; }

	/**
	 * Wager identification. All bets placed thru the API will have value 1.
	 * Website supports multiple bets (wagers) placement in the same bet slip. 
	 * In that case the bet would have appropriate wager number.
	 */
	private Integer wagerNumber;
	public Integer wagerNumber () { return this.wagerNumber; }

	/**
	 * Date time when the bet was placed.
	 */
	private Instant placedAt;
	public Instant placedAt () { return this.placedAt; }

	/**
	 * Win amount.
	 */
	private BigDecimal win;
	public BigDecimal win () { return this.win; }

	/**
	 * Risk amount.
	 */
	private BigDecimal risk;
	public BigDecimal risk () { return this.risk; }

	/**
	 * Win-Loss for settled bets.
	 */
	private Optional<BigDecimal> winLoss;
	public Optional<BigDecimal> winLoss () { return this.winLoss; }

	/**
	 * Status of the bet.
	 */
	private BET_STATUS betStatus;
	public BET_STATUS betStatus () { return this.betStatus; }

	/**
	 * Type of the bet.
	 */
	private BET_TYPE betType;
	public BET_TYPE betType () { return this.betType; }

	/**
	 * Sport ID.
	 */
	private Integer sportId;
	public Integer sportId () { return this.sportId; }

	/**
	 * League ID.
	 */
	private Integer leagueId;
	public Integer leagueId () { return this.leagueId; }

	/**
	 * Event ID.
	 */
	private Integer eventId;
	public Integer eventId () { return this.eventId; }

	/**
	 * Handicap that the bet was placed on.
	 */
	private Optional<BigDecimal> handicap;
	public Optional<BigDecimal> handicap () { return this.handicap; }

	/**
	 * Price that the bet was placed on.
	 */
	private BigDecimal price;
	public BigDecimal price () { return this.price; }

	/**
	 * Team name. If the bet was placed on moneyline draw side, the value will be Draw.
	 */
	private Optional<String> teamName;
	public Optional<String> teamName () { return this.teamName; }

	/**
	 * Type of side.
	 */
	private Optional<SIDE_TYPE> side;
	public Optional<SIDE_TYPE> side () { return this.side; }

	/**
	 * Bet odds format.
	 */
	private ODDS_FORMAT oddsFormat;
	public ODDS_FORMAT oddsFormat () { return this.oddsFormat; }

	/**
	 * Client’s commission on the bet.
	 */
	private Optional<BigDecimal> clientCommission;
	public Optional<BigDecimal> clientCommission () { return this.clientCommission; }

	/**
	 * Team 1 pitcher name. Baseball only.
	 */
	private Optional<String> pitcher1;
	public Optional<String> pitcher1 () { return this.pitcher1; }

	/**
	 * Team 2 pitcher name. Baseball only.
	 */
	private Optional<String> pitcher2;
	public Optional<String> pitcher2 () { return this.pitcher2; }

	/**
	 * Whether the team 1 pitcher must start in order for bet to take action. Baseball only.
	 */
	private Optional<Boolean> pitcher1MustStart;
	public Optional<Boolean> pitcher1MustStart () { return this.pitcher1MustStart; }

	/**
	 * Whether the team 2 pitcher must start in order for bet to take action. Baseball only.
	 */
	private Optional<Boolean> pitcher2MustStart;
	public Optional<Boolean> pitcher2MustStart () { return this.pitcher2MustStart; }

	/**
	 * Team 1 name.
	 */
	private String team1;
	public String team1 () { return this.team1; }

	/**
	 * Team 2 name.
	 */
	private String team2;
	public String team2 () { return this.team2; }

	/**
	 * Whether the bet is on live event.
	 */
	private Optional<Boolean> isLive;
	public Optional<Boolean> isLive () { return this.isLive; }

	/**
	 * Period number that the bet was placed on.
	 */
	private Optional<Integer> periodNumber;
	public Optional<Integer> periodNumber () { return this.periodNumber; }

	// pinaclesports.com defines these parameters below here as 'required' but they are optional virtually.
	/**
	 * Team 1 score that the bet was placed on.
	 */
	private Optional<BigDecimal> team1Score;
	public Optional<BigDecimal> team1Score () { return this.team1Score; }

	/**
	 * Team 2 score that the bet was placed on.
	 */
	private Optional<BigDecimal> team2Score;
	public Optional<BigDecimal> team2Score () { return this.team2Score; }

	/**
	 * Full time team 1 score.
	 */
	private Optional<BigDecimal> ftTeam1Score;
	public Optional<BigDecimal> ftTeam1Score () { return this.ftTeam1Score; }

	/**
	 * Full time team 2 score.
	 */
	private Optional<BigDecimal> ftTeam2Score;
	public Optional<BigDecimal> ftTeam2Score () { return this.ftTeam2Score; }

	/**
	 * End of period team 1 score. If the bet was placed on Game period (periodNumber =0),
	 * this will be null.
	 */
	private Optional<BigDecimal> pTeam1Score;
	public Optional<BigDecimal> pTeam1Score () { return this.pTeam1Score; }

	/**
	 * End of period team 2 score. If the bet was placed on Game period (periodNumber =0),
	 * this will be null.
	 */
	private Optional<BigDecimal> pTeam2Score;
	public Optional<BigDecimal> pTeam2Score () { return this.pTeam2Score; }
	
	private Bet (Json json) {
		this.betId = json.getAsInteger("betId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.betId"));
		this.wagerNumber = json.getAsInteger("wagerNumber")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.wagerNumber"));
		this.placedAt = json.getAsString("placedAt")
				.map(datetime -> datetime + "Z") // TODO check why their response is without 'Z'
				.map(Instant::parse)
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.placedAt"));
		this.win = json.getAsBigDecimal("win")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.win"));
		this.risk = json.getAsBigDecimal("risk")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.risk"));
		this.winLoss = json.getAsBigDecimal("winLoss");
		this.betStatus = json.getAsString("betStatus")
				.map(BET_STATUS::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.betStatus"));
		this.betType = json.getAsString("betType")
				.map(BET_TYPE::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.betType"));
		this.sportId = json.getAsInteger("sportId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.sportId"));
		this.leagueId = json.getAsInteger("leagueId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.leagueId"));
		this.eventId = json.getAsInteger("eventId")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.eventId"));
		this.handicap = json.getAsBigDecimal("handicap");
		this.price = json.getAsBigDecimal("price")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.price"));
		this.teamName = json.getAsString("teamName");
		this.side = json.getAsString("side")
				.map(SIDE_TYPE::valueOf);
		this.oddsFormat = json.getAsString("oddsFormat")
				.map(ODDS_FORMAT::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.oddsFormat"));
		this.clientCommission = json.getAsBigDecimal("clientCommission");
		this.pitcher1 = json.getAsString("pitcher1");
		this.pitcher2 = json.getAsString("pitcher2");
		this.pitcher1MustStart = json.getAsString("pitcher1MustStart")
				.map(Enums::boolean1);
		this.pitcher2MustStart = json.getAsString("pitcher2MustStart")
				.map(Enums::boolean1);
		this.team1 = json.getAsString("team1")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.team1"));
		this.team2 = json.getAsString("team2")
				.orElseThrow(() -> NoNecessaryKeyException.of("Bet.team2"));
		this.isLive = json.getAsString("isLive")
				.map(Enums::boolean1);
		this.periodNumber = json.getAsInteger("periodNumber");
		this.team1Score = json.getAsBigDecimal("team1Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.team1Score"));
		this.team2Score = json.getAsBigDecimal("team2Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.team2Score"));
		this.ftTeam1Score = json.getAsBigDecimal("ftTeam1Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.ftTeam1Score"));
		this.ftTeam2Score = json.getAsBigDecimal("ftTeam2Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.ftTeam2Score"));
		this.pTeam1Score = json.getAsBigDecimal("pTeam1Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.pTeam1Score"));
		this.pTeam2Score = json.getAsBigDecimal("pTeam2Score");
				//.orElseThrow(() -> NoNecessaryKeyException.of("Bet.pTeam2Score"));
	}
	
	static List<Bet> parse (String text) throws PinnacleException {
		return text.equals("")
				? new ArrayList<>()
				: parseBets(Json.of(text));
	}

	private static List<Bet> parseBets (Json json) {
		return json.getAsJsonStream("bets")
				.map(Bet::new)
				.collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return "Bet [betId=" + betId + ", wagerNumber=" + wagerNumber
				+ ", placedAt=" + placedAt + ", win=" + win + ", risk=" + risk
				+ ", winLoss=" + winLoss + ", betStatus=" + betStatus
				+ ", betType=" + betType + ", sportId=" + sportId
				+ ", leagueId=" + leagueId + ", eventId=" + eventId
				+ ", handicap=" + handicap + ", price=" + price + ", teamName="
				+ teamName + ", side=" + side + ", oddsFormat=" + oddsFormat
				+ ", clientCommission=" + clientCommission + ", pitcher1="
				+ pitcher1 + ", pitcher2=" + pitcher2 + ", pitcher1MustStart="
				+ pitcher1MustStart + ", pitcher2MustStart="
				+ pitcher2MustStart + ", team1=" + team1 + ", team2=" + team2
				+ ", isLive=" + isLive + ", periodNumber=" + periodNumber
				+ ", team1Score=" + team1Score + ", team2Score=" + team2Score
				+ ", ftTeam1Score=" + ftTeam1Score + ", ftTeam2Score="
				+ ftTeam2Score + ", pTeam1Score=" + pTeam1Score
				+ ", pTeam2Score=" + pTeam2Score + "]";
	}
}
