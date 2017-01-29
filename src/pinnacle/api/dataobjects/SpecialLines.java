package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.Optional;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.GETLINE_RESPONSE_STATUS;

public class SpecialLines extends AbstractDataObject {

	private GETLINE_RESPONSE_STATUS status;

	public GETLINE_RESPONSE_STATUS status() {
		return this.status;
	}

	private Long specialId;

	public Long specialId() {
		return this.specialId;
	}

	private Long contestantId;

	public Long contestantId() {
		return this.contestantId;
	}

	private BigDecimal maxRiskStake;

	public BigDecimal maxRiskStake() {
		return this.maxRiskStake;
	}

	private BigDecimal minRiskStake;

	public BigDecimal minRiskStake() {
		return this.minRiskStake;
	}

	private BigDecimal maxWinStake;

	public BigDecimal maxWinStake() {
		return this.maxWinStake;
	}

	private BigDecimal minWinStake;

	public BigDecimal minWinStake() {
		return this.minWinStake;
	}

	private Long lineId;

	public Long lineId() {
		return this.lineId;
	}

	private BigDecimal price;

	public BigDecimal price() {
		return this.price;
	}

	private Optional<BigDecimal> handicap;

	public Optional<BigDecimal> handicap() {
		return this.handicap;
	}

	private SpecialLines(Json json) {
		this.status = json.getAsString("status").map(GETLINE_RESPONSE_STATUS::fromAPI).orElse(null);
		this.specialId = json.getAsLong("specialId").orElse(null);
		this.contestantId = json.getAsLong("contestantId").orElse(null);
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake").orElse(null);
		this.minRiskStake = json.getAsBigDecimal("minRiskStake").orElse(null);
		this.maxWinStake = json.getAsBigDecimal("maxWinStake").orElse(null);
		this.minWinStake = json.getAsBigDecimal("minWinStake").orElse(null);
		this.lineId = json.getAsLong("lineId").orElse(null);
		this.price = json.getAsBigDecimal("price").orElse(null);
		this.handicap = json.getAsBigDecimal("handicap");
		this.checkRequiredKeys();
	}

	private SpecialLines() {
		this.isEmpty = true;
	}

	public static SpecialLines parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new SpecialLines() : new SpecialLines(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null || this.specialId == null || this.contestantId == null || this.maxRiskStake == null
				|| this.minRiskStake == null || this.maxWinStake == null || this.minWinStake == null
				|| this.lineId == null || this.price == null;
	}

	@Override
	public String toString() {
		return "SpecialLines [status=" + status + ", specialId=" + specialId + ", contestantId=" + contestantId
				+ ", maxRiskStake=" + maxRiskStake + ", minRiskStake=" + minRiskStake + ", maxWinStake=" + maxWinStake
				+ ", minWinStake=" + minWinStake + ", lineId=" + lineId + ", price=" + price + ", handicap=" + handicap
				+ "]";
	}
}
