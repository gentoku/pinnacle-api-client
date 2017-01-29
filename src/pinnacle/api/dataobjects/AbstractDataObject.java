package pinnacle.api.dataobjects;

public abstract class AbstractDataObject {

	protected boolean isFlawed = false;

	public boolean isFlawed() {
		return this.isFlawed;
	}

	public boolean isNotFlawed() {
		return !this.isFlawed();
	}

	protected boolean isEmpty;

	public boolean isEmpty() {
		return this.isEmpty;
	}

	public boolean isNotEmpty() {
		return !this.isEmpty();
	}

	public boolean isNotEmptyAndFlawed() {
		return this.isNotEmpty() && this.isNotFlawed();
	}

	/*
	 * This is annoying process. Wanna do at orElse in lambda.
	 */
	protected void checkRequiredKeys() {
		if (this.hasRequiredKeyWithNull())
			this.isFlawed = true;
	};

	abstract boolean hasRequiredKeyWithNull();
}
