package org.helmj.support.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.helmj.ConfigAware;

@Builder
@Getter
@Setter
public class ClientConfig implements ConfigAware {

	private String connectorType;

	private String clientType;

}
