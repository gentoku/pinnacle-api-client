package pinnacle.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.Enums.PLACE_PARLAY_BET_ERROR_CODE;
import pinnacle.api.Enums.PLACE_PARLAY_BET_RESPONSE_STATUS;
import pinnacle.api.Parlay.Leg;
import pinnacle.api.Parlay.RoundRobinOption;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class PlacedParlayBet {

	/**
	 * Response status.
	 */
	private PLACE_PARLAY_BET_RESPONSE_STATUS status;
	public PLACE_PARLAY_BET_RESPONSE_STATUS status () { return this.status; }

	/**
	 * If Status is PROCESSED_WITH_ERROR , errorCode will be in the response.
	 */
	private Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode;
	public Optional<PLACE_PARLAY_BET_ERROR_CODE> errorCode () { return this.errorCode; }

	/**
	 * The bet ID of the new bet. May be empty on failure.
	 */
	private Optional<Integer> betId;
	public  Optional<Integer> betId () { return this.betId; }

	/**
	 * Echo of the uniqueRequestId from the request.
	 */
	private String uniqueRequestId;
	public String uniqueRequestId () { return this.uniqueRequestId; }

	/**
	 * Provides array with all acceptable Round Robin options 
	 * with parlay odds for that option.
	 */
	private List<RoundRobinOption> roundRobinOptionWithOdds = new ArrayList<>();
	public List<RoundRobinOption> roundRobinOptionWithOdds () { return this.roundRobinOptionWithOdds; }

	/**
	 * Collection of valid legs. Can be empty if no valid legs found.
	 */
	private List<Leg> validLegs = new ArrayList<>();
	public List<Leg> validLegs () { return this.validLegs; }

	/**
	 * The collection of legs that resulted in error. Can be empty if no invalid legs found.
	 */
	private List<Leg> invalidLegs = new ArrayList<>();
	public List<Leg> invalidLegs () { return this.invalidLegs; }
	
	private PlacedParlayBet (Json json) {
		this.status = json.getAsString("status")
				.map(PLACE_PARLAY_BET_RESPONSE_STATUS::valueOf)
				.orElseThrow(() -> NoNecessaryKeyException.of("PlacedParlayBet.status"));
		this.errorCode = json.getAsString("errorCode")
				.map(PLACE_PARLAY_BET_ERROR_CODE::valueOf);
		this.betId = json.getAsInteger("betId");
		this.uniqueRequestId = json.getAsString("uniqueRequestId")
				.orElseThrow(() -> NoNecessaryKeyException.of("PlacedParlayBet.uniqueRequestId"));
		this.roundRobinOptionWithOdds = json.getAsJsonStream("roundRobinOptionWithOdds")
				.map(RoundRobinOption::parse)
				.collect(Collectors.toList());
		this.validLegs = json.getAsJsonStream("validLegs")
				.map(Leg::parse)
				.collect(Collectors.toList());
		this.invalidLegs = json.getAsJsonStream("invalidLegs")
				.map(Leg::parse)
				.collect(Collectors.toList());
	}
	
	static PlacedParlayBet parse (String text) throws PinnacleException {
		if (text.equals("")) throw new PinnacleException("Empty response for Place Parlay Bet.");
		return new PlacedParlayBet(Json.of(text));
	}
	
	@Override
	public String toString() {
		return "PlacedParlayBet [status=" + status + ", errorCode=" + errorCode
				+ ", betId=" + betId + ", uniqueRequestId=" + uniqueRequestId
				+ ", roundRobinOptionWithOdds=" + roundRobinOptionWithOdds
				+ ", validLegs=" + validLegs + ", invalidLegs=" + invalidLegs
				+ "]";
	}
}
