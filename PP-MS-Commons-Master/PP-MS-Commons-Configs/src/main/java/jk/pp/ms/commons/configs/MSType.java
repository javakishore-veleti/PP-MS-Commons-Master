package jk.pp.ms.commons.configs;

public enum MSType {

	ENTITLEMENTS("entitlements"), ECLAIMS("eclaims"), PROVIDERS("providers"), MEDPOLICY("medpolicy");

	private String value;

	private MSType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
