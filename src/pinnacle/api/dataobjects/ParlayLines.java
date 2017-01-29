package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.PARLAY_LINES_STATUS;
import pinnacle.api.enums.PLACE_PARLAY_BET_ERROR_CODE;
import pinnacle.api.enums.PLACE_PARLAY_LEG_ERROR_CODE;
import pinnacle.api.enums.PLACE_PARLAY_LEG_RESPONSE_STATUS;
import pinnacle.api.enums.ROUND_ROBIN_OPTIONS;

public class ParlayLines extends AbstractDataObject {

	private PARLAY_LINES_STATUS status;

	public PARLAY_LINES_STATUS status() {
		return this.status;
	}

	private Optional<PLACE_PARLAY_BET_ERROR_CODE> error;

	public Optional<PLACE_PARLAY_BET_ERROR_CODE> error() {
		return this.error;
	}

	private Optional<BigDecimal> maxRiskStake;

	public Optional<BigDecimal> maxRiskStake() {
		return this.maxRiskStake;
	}

	private Optional<BigDecimal> minRiskStake;

	public Optional<BigDecimal> minRiskStake() {
		return this.minRiskStake;
	}

	private List<RoundRobinOdds> roundRobinOptionWithOdds = new ArrayList<>();

	public List<RoundRobinOdds> roundRobinOptionWithOdds() {
		return this.roundRobinOptionWithOdds;
	}

	public Stream<RoundRobinOdds> roundRobinOptionWithOddsAsStream() {
		return this.roundRobinOptionWithOdds.stream();
	}

	private List<Leg> legs = new ArrayList<>();

	public List<Leg> legs() {
		return this.legs;
	}

	public Stream<Leg> legsAsStream() {
		return this.legs.stream();
	}

	private ParlayLines(Json json) {
		this.status = json.getAsString("status").map(PARLAY_LINES_STATUS::fromAPI).orElse(null);
		this.error = json.getAsString("error").map(PLACE_PARLAY_BET_ERROR_CODE::fromAPI);
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake");
		this.minRiskStake = json.getAsBigDecimal("minRiskStake");
		this.roundRobinOptionWithOdds = json.getAsJsonStream("roundRobinOptionWithOdds").map(RoundRobinOdds::new)
				.collect(Collectors.toList());
		this.legs = json.getAsJsonStream("legs").map(Leg::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private ParlayLines() {
		this.isEmpty = true;
	}

	public static ParlayLines parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new ParlayLines() : new ParlayLines(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null;
	}

	@Override
	public String toString() {
		return "ParlayLines [status=" + status + ", error=" + error + ", maxRiskStake=" + maxRiskStake
				+ ", minRiskStake=" + minRiskStake + ", roundRobinOptionWithOdds=" + roundRobinOptionWithOdds
				+ ", legs=" + legs + "]";
	}

	public static class Leg extends AbstractDataObject {

		private PLACE_PARLAY_LEG_RESPONSE_STATUS status;

		public PLACE_PARLAY_LEG_RESPONSE_STATUS status() {
			return this.status;
		}

		private Optional<PLACE_PARLAY_LEG_ERROR_CODE> errorCode;

		public Optional<PLACE_PARLAY_LEG_ERROR_CODE> errorCode() {
			return this.errorCode;
		}

		private String legId;

		public String legId() {
			return this.legId;
		}

		private List<String> correlatedLegs = new ArrayList<>();

		public List<String> correlatedLegs() {
			return this.correlatedLegs;
		}

		public Stream<String> correlatedLegsAsStream() {
			return this.correlatedLegs.stream();
		}

		private Optional<Long> lineId;

		public Optional<Long> lineId() {
			return this.lineId;
		}

		private Optional<Long> altLineId;

		public Optional<Long> altLineId() {
			return this.altLineId;
		}

		private Optional<BigDecimal> price;

		public Optional<BigDecimal> price() {
			return this.price;
		}

		private Leg(Json json) {
			this.status = json.getAsString("status").map(PLACE_PARLAY_LEG_RESPONSE_STATUS::fromAPI).orElse(null);
			this.errorCode = json.getAsString("errorCode").map(PLACE_PARLAY_LEG_ERROR_CODE::fromAPI);
			this.legId = json.getAsString("legId").orElse(null);
			this.correlatedLegs = json.getAsStringStream("correlatedLegs").collect(Collectors.toList());
			this.lineId = json.getAsLong("lineId");
			this.altLineId = json.getAsLong("altLineId");
			this.price = json.getAsBigDecimal("price");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.legId == null;
		}

		@Override
		public String toString() {
			return "Leg [status=" + status + ", errorCode=" + errorCode + ", legId=" + legId + ", correlatedLegs="
					+ correlatedLegs + ", lineId=" + lineId + ", altLineId=" + altLineId + ", price=" + price + "]";
		}
	}

	public static class RoundRobinOdds extends AbstractDataObject {

		private ROUND_ROBIN_OPTIONS roundRobinOption;

		public ROUND_ROBIN_OPTIONS roundRobinOption() {
			return this.roundRobinOption;
		}

		private BigDecimal odds;

		public BigDecimal odds() {
			return this.odds;
		}

		private RoundRobinOdds(Json json) {
			this.roundRobinOption = json.getAsString("roundRobinOption").map(ROUND_ROBIN_OPTIONS::fromAPI).orElse(null);
			this.odds = json.getAsBigDecimal("odds").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.roundRobinOption == null || this.odds == null;
		}

		@Override
		public String toString() {
			return "RoundRobinOdds [roundRobinOption=" + roundRobinOption + ", odds=" + odds + "]";
		}
	}
}
