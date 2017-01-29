package pinnacle.api;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import pinnacle.api.Enums.GETLINE_RESPONSE_STATUS;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Line {

	/**
	 * If the value is NOT_EXISTS, then this will be the only parameter in the response. 
	 * All other parameters would be empty.
	 */
	private GETLINE_RESPONSE_STATUS status;
	public GETLINE_RESPONSE_STATUS status() { return this.status; }

	/**
	 * Latest price.
	 */
	private Optional<BigDecimal> price;
	public Optional<BigDecimal> price() { return this.price; }

	/**
	 * Line identification needed to place a bet.
	 */
	private Optional<Integer> lineId;
	public Optional<Integer> lineId() { return this.lineId; }

	/**
	 * This would be needed to place the bet if the handicap is on alternate line, 
	 * otherwise it will not be in the response.
	 */
	private Optional<Integer> altLineId;
	public Optional<Integer> altLineId() { return this.altLineId; }

	// TODO Contradicted about the relation between team1/team2 and home/away in manual.
	/**
	 * Home team score. Applicable to soccer only.
	 */
	private Optional<Integer> team1Score;
	public Optional<Integer> team1Score() { return this.team1Score; }

	/**
	 * Away team score. Applicable to soccer only.
	 */
	private Optional<Integer> team2Score;
	public Optional<Integer> team2Score() { return this.team2Score; }

	/**
	 * Home team red cards. Applicable to soccer only.
	 */
	private Optional<Integer> team1RedCards;
	public Optional<Integer> team1RedCards() { return this.team1RedCards; }

	/**
	 * Away team red cards. Applicable to soccer only.
	 */
	private Optional<Integer> team2RedCards;
	public Optional<Integer> team2RedCards () { return this.team2RedCards; }

	/**
	 * Maximum bettable risk amount.
	 */
	private Optional<BigDecimal> maxRiskStake;
	public Optional<BigDecimal> maxRiskStake() { return this.maxRiskStake; }

	/**
	 * Minimum bettable risk amount.
	 */
	private Optional<BigDecimal> minRiskStake;
	public Optional<BigDecimal> minRiskStake() { return this.minRiskStake; }

	/**
	 * Maximum bettable win amount.
	 */
	private Optional<BigDecimal> maxWinStake;
	public Optional<BigDecimal> maxWinStake() { return this.maxWinStake; }

	/**
	 * Minimum bettable win amount.
	 */
	private Optional<BigDecimal> minWinStake;
	public Optional<BigDecimal> minWinStake() { return this.minWinStake; }

	/**
	 * Line is effective as of this date and time.
	 */
	private Optional<Instant> effectiveAsOf;
	public Optional<Instant> effectiveAsOf() { return this.effectiveAsOf; }
	
	private Line () {}
	
	private Line (Json json) {
		this.status = json.getAsString("status")
				.map(GETLINE_RESPONSE_STATUS::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("Line.status"));
		this.price = json.getAsBigDecimal("price");
		this.lineId = json.getAsInteger("lineId");
		this.altLineId = json.getAsInteger("altLineId");
		this.team1Score = json.getAsInteger("team1Score");
		this.team2Score = json.getAsInteger("team2Score");
		this.team1RedCards = json.getAsInteger("team1RedCards");
		this.team2RedCards = json.getAsInteger("team2RedCards");
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake");
		this.minRiskStake = json.getAsBigDecimal("minRiskStake");
		this.maxWinStake = json.getAsBigDecimal("maxWinStake");
		this.minWinStake = json.getAsBigDecimal("minWinStake");
		this.effectiveAsOf = json.getAsString("effectiveAsOf")
				.map(datetime -> datetime + "Z") // TODO check why their response is without 'Z' 
				.map(Instant::parse);
	}
	
	static Line parse (String text) throws PinnacleException {
		return text.equals("")
				? new Line()
				: new Line(Json.of(text));
	}
	
	@Override
	public String toString() {
		return "Line [status=" + status + ", price=" + price + ", lineId="
				+ lineId + ", altLineId=" + altLineId + ", team1Score="
				+ team1Score + ", team2Score=" + team2Score
				+ ", team1RedCards=" + team1RedCards + ", team2RedCards="
				+ team2RedCards + ", maxRiskStake=" + maxRiskStake
				+ ", minRiskStake=" + minRiskStake + ", maxWinStake="
				+ maxWinStake + ", minWinStake=" + minWinStake
				+ ", effectiveAsOf=" + effectiveAsOf + "]";
	}
}
