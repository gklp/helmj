package com.helmj.support.client.types;

import java.util.EnumSet;

public enum ConnectorType {
	FABRIC("io.fabric8.kubernetes");

	String connectorType;

	ConnectorType(String connectorType) {
		this.connectorType = connectorType;
	}

	public static ConnectorType getType(String connectorType) {
		return EnumSet.allOf(ConnectorType.class)
				.stream()
				.filter(e -> e.connectorType.equals(connectorType))
				.findFirst()
				.orElse(null);
	}

}
