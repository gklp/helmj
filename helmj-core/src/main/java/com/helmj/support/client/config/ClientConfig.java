package com.helmj.support.client.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientConfig {

	private String connectorType;

	private String clientType;

}
