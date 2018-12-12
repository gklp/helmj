package com.helmj.support.client.types;

import java.util.EnumSet;

public enum ClientType {
	BLOCKING_CLIENT("blocking-client");

	private String clientType;

	ClientType(String clientType) {
		this.clientType = clientType;
	}

	public static ClientType getType(String clientType) {
		return EnumSet.allOf(ClientType.class)
				.stream()
				.filter(e -> e.clientType.equals(clientType))
				.findFirst()
				.orElse(null);
	}

}
