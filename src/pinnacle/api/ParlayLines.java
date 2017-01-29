package pinnacle.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.Enums.PLACE_PARLAY_BET_ERROR_CODE;
import pinnacle.api.Enums.PLACE_PARLAY_LEG_RESPONSE_STATUS;
import pinnacle.api.Parlay.Leg;
import pinnacle.api.Parlay.RoundRobinOption;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class ParlayLines {

	/**
	 * Response status.
	 */
	private PLACE_PARLAY_LEG_RESPONSE_STATUS status; // TODO Contradiction between the type and example response in manual.
	public PLACE_PARLAY_LEG_RESPONSE_STATUS status () { return this.status; }

	/**
	 * If Status is PROCESSED_WITH_ERROR , errorCode will be in the response.
	 */
	private Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode;
	public Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode () { return this.errorCode; }
	
	/**
	 * Maximum allowed stake amount.
	 */
	private Optional<BigDecimal> maxRiskStake;
	public Optional<BigDecimal> maxRiskStake () { return this.maxRiskStake; }
	
	/**
	 * Minimum allowed stake amount.
	 */
	private Optional<BigDecimal> minRiskStake;
	public Optional<BigDecimal> minRiskStake () { return this.minRiskStake; }
	
	/**
	 * Provides array with all acceptable Round Robin options 
	 * with parlay odds for that option.
	 */
	private List<RoundRobinOption> roundRobinOptionWithOdds = new ArrayList<>();
	public List<RoundRobinOption> roundRobinOptionWithOdds () { return this.roundRobinOptionWithOdds; }

	/**
	 * The collection of legs.
	 */
	private List<Leg> legs = new ArrayList<>();
	public List<Leg> legs () { return this.legs; }
	
	private ParlayLines () {}
	
	private ParlayLines (Json json) {
		this.status = json.getAsString("status")
				.map(PLACE_PARLAY_LEG_RESPONSE_STATUS::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("PlacedParlayBet.status"));
		this.errorCode = json.getAsString("errorCode")
				.map(PLACE_PARLAY_BET_ERROR_CODE::valueOf);
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake");
		this.minRiskStake = json.getAsBigDecimal("minRiskStake");
		this.roundRobinOptionWithOdds = json.getAsJsonStream("roundRobinOptionWithOdds")
				.map(RoundRobinOption::parse)
				.collect(Collectors.toList());
		this.legs = json.getAsJsonStream("legs")
				.map(Leg::parse)
				.collect(Collectors.toList());
	}
	
	static ParlayLines parse (String text) throws PinnacleException {
		return text.equals("")
				? new ParlayLines()
				: new ParlayLines(Json.of(text));
	}

	@Override
	public String toString() {
		return "ParlayLines [status=" + status + ", errorCode=" + errorCode
				+ ", maxRiskStake=" + maxRiskStake + ", minRiskStake="
				+ minRiskStake + ", roundRobinOptionWithOdds="
				+ roundRobinOptionWithOdds + ", legs=" + legs + "]";
	}
}
