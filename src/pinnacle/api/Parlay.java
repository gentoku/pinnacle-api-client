package pinnacle.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pinnacle.api.Enums.PLACE_PARLAY_LEG_RESPONSE_STATUS;
import pinnacle.api.Enums.PLACE_PARLEY_LEG_ERROR_CODE;
import pinnacle.api.Enums.ROUND_ROBIN_OPTIONS;
import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class Parlay {

	public static class Leg {
		
		/**
		 * Response status for the leg.
		 */
		private PLACE_PARLAY_LEG_RESPONSE_STATUS status;
		public PLACE_PARLAY_LEG_RESPONSE_STATUS status () { return this.status; }
		
		/**
		 * If Status is PROCESSED_WITH_ERROR , errorCode will be in the response.
		 */
		private Optional<PLACE_PARLEY_LEG_ERROR_CODE> errorCode;
		public Optional<PLACE_PARLEY_LEG_ERROR_CODE> errorCode () { return this.errorCode; }
		
		/**
		 * Echo of the legId from the request.
		 */
		private String legId;
		public String legId () { return this.legId; }
		
		/**
		 * If errorCode is CORRELATED will contain legIds of all correlated legs.
		 */
		private List<String> correlatedLegs;
		public List<String> correlatedLegs () { return this.correlatedLegs; }
		
		/**
		 * Line identification that bet was placed on.
		 */
		private Optional<Integer> lineId;
		public Optional<Integer> lineId () { return this.lineId; }
		
		/**
		 * If Bet was placed on Alternate Line the Id of that line will be returned.
		 */
		private Optional<Integer> altLineId;
		public Optional<Integer> altLineId () { return this.altLineId; }
		
		/**
		 * Price that the bet was placed on.
		 */
		private Optional<BigDecimal> price;
		public Optional<BigDecimal> price () { return this.price; }
		
		private Leg (Json json) {
			this.status = json.getAsString("status")
					.map(PLACE_PARLAY_LEG_RESPONSE_STATUS::valueOf)
					.orElseThrow(() -> NoNecessaryKeyException.of("Parlay.Leg.status"));
			this.errorCode = json.getAsString("errorCode")
					.map(PLACE_PARLEY_LEG_ERROR_CODE::valueOf);
			this.legId = json.getAsString("legId")
					.orElseThrow(() -> NoNecessaryKeyException.of("Parlay.Leg.legId"));
			this.correlatedLegs = json.getAsStringStream("correlatedLegs")
					.collect(Collectors.toList());
			this.lineId = json.getAsInteger("lineId");
			this.altLineId = json.getAsInteger("altLineId");
			this.price = json.getAsBigDecimal("price");
		}
		
		static Leg parse (Json json) {
			return new Leg(json);
		}

		@Override
		public String toString() {
			return "Leg [status=" + status + ", errorCode=" + errorCode
					+ ", legId=" + legId + ", correlatedLegs=" + correlatedLegs
					+ ", lineId=" + lineId + ", altLineId=" + altLineId
					+ ", price=" + price + "]";
		}
	}

	public static class RoundRobinOption {
		/**
		 * Round Robin option.
		 */
		private ROUND_ROBIN_OPTIONS roundRobinOption;
		public ROUND_ROBIN_OPTIONS roundRobinOption () { return this.roundRobinOption; }
		
		/**
		 * Odds for Round Robin option in requested Odds format.
		 */
		private BigDecimal odds;
		public BigDecimal odds () { return this.odds; }
		
		private RoundRobinOption (Json json) {
			this.roundRobinOption = json.getAsString("RoundRobinOption")
					.map(ROUND_ROBIN_OPTIONS::valueOf)
					.orElseThrow(() -> NoNecessaryKeyException.of("Parlay.RoundRobinOption.RoundRobinOption"));
			this.odds = json.getAsBigDecimal("Odds")
					.orElseThrow(() -> NoNecessaryKeyException.of("Parlay.RoundRobinOption.Odds"));
		}
		
		static RoundRobinOption parse (Json json) {
			return new RoundRobinOption(json);
		}

		@Override
		public String toString() {
			return "RoundRobinOption [roundRobinOption=" + roundRobinOption
					+ ", odds=" + odds + "]";
		}
		
	}
	
}
