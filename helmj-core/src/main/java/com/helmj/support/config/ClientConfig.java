package com.helmj.support.config;

import com.helmj.ConfigAware;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientConfig implements ConfigAware {

	private String connectorType;

	private String clientType;

}
