package pinnacle.api.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class CancellationReasons extends AbstractDataObject {

	private List<CancellationReason> cancellationReasons = new ArrayList<>();

	public List<CancellationReason> cancellationReasons() {
		return this.cancellationReasons;
	}

	public Stream<CancellationReason> cancellationReasonsAsStream() {
		return this.cancellationReasons.stream();
	}

	private CancellationReasons(Json json) {
		this.cancellationReasons = json.getAsJsonStream("cancellationReasons").map(CancellationReason::new)
				.collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private CancellationReasons() {
		this.isEmpty = true;
	}

	public static CancellationReasons parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new CancellationReasons() : new CancellationReasons(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "CancellationReasons [cancellationReasons=" + cancellationReasons + "]";
	}

	public static class CancellationReason extends AbstractDataObject {

		private String code;

		public String code() {
			return this.code;
		}

		private String description;

		public String description() {
			return this.description;
		}

		private CancellationReason(Json json) {
			this.code = json.getAsString("code").orElse(null);
			this.description = json.getAsString("description").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.code == null || this.description == null;
		}

		@Override
		public String toString() {
			return "CancellationReason [code=" + code + ", description=" + description + "]";
		}
	}
}
