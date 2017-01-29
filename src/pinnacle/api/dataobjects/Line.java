package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.GETLINE_RESPONSE_STATUS;

public class Line extends AbstractDataObject {

	private GETLINE_RESPONSE_STATUS status;

	public GETLINE_RESPONSE_STATUS status() {
		return this.status;
	}

	private Optional<BigDecimal> price;

	public Optional<BigDecimal> price() {
		return this.price;
	}

	private Optional<Long> lineId;

	public Optional<Long> lineId() {
		return this.lineId;
	}

	private Optional<Long> altLineId;

	public Optional<Long> altLineId() {
		return this.altLineId;
	}

	private Optional<Integer> team1Score;

	public Optional<Integer> team1Score() {
		return this.team1Score;
	}

	private Optional<Integer> team2Score;

	public Optional<Integer> team2Score() {
		return this.team2Score;
	}

	private Optional<Integer> team1RedCards;

	public Optional<Integer> team1RedCards() {
		return this.team1RedCards;
	}

	private Optional<Integer> team2RedCards;

	public Optional<Integer> team2RedCards() {
		return this.team2RedCards;
	}

	private Optional<BigDecimal> maxRiskStake;

	public Optional<BigDecimal> maxRiskStake() {
		return this.maxRiskStake;
	}

	private Optional<BigDecimal> minRiskStake;

	public Optional<BigDecimal> minRiskStake() {
		return this.minRiskStake;
	}

	private Optional<BigDecimal> maxWinStake;

	public Optional<BigDecimal> maxWinStake() {
		return this.maxWinStake;
	}

	private Optional<BigDecimal> minWinStake;

	public Optional<BigDecimal> minWinStake() {
		return this.minWinStake;
	}

	private Optional<Instant> effectiveAsOf;

	public Optional<Instant> effectiveAsOf() {
		return this.effectiveAsOf;
	}

	private Line(Json json) {
		this.status = json.getAsString("status").map(GETLINE_RESPONSE_STATUS::fromAPI).orElse(null);
		this.price = json.getAsBigDecimal("price");
		this.lineId = json.getAsLong("lineId");
		this.altLineId = json.getAsLong("altLineId");
		this.team1Score = json.getAsInteger("team1Score");
		this.team2Score = json.getAsInteger("team2Score");
		this.team1RedCards = json.getAsInteger("team1RedCards");
		this.team2RedCards = json.getAsInteger("team2RedCards");
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake");
		this.minRiskStake = json.getAsBigDecimal("minRiskStake");
		this.maxWinStake = json.getAsBigDecimal("maxWinStake");
		this.minWinStake = json.getAsBigDecimal("minWinStake");
		this.effectiveAsOf = json.getAsString("effectiveAsOf").map(s -> s + "Z").map(Instant::parse);
		this.checkRequiredKeys();
	}

	private Line() {
		this.isEmpty = true;
	}

	public static Line parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Line() : new Line(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null;
	}

	@Override
	public String toString() {
		return "Line [status=" + status + ", price=" + price + ", lineId=" + lineId + ", altLineId=" + altLineId
				+ ", team1Score=" + team1Score + ", team2Score=" + team2Score + ", team1RedCards=" + team1RedCards
				+ ", team2RedCards=" + team2RedCards + ", maxRiskStake=" + maxRiskStake + ", minRiskStake="
				+ minRiskStake + ", maxWinStake=" + maxWinStake + ", minWinStake=" + minWinStake + ", effectiveAsOf="
				+ effectiveAsOf + "]";
	}
}
