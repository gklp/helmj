package com.helmj.connector.config;

import com.helmj.support.config.ClusterWith;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FabricConnectorConfig extends AbstractConnectorConfig<Config> {

	private FabricConnectorConfig(ClusterWith clientWith) {
		super(clientWith);
	}

	@Builder(builderMethodName = "fabricBuilder")
	public static FabricConnectorConfig newFabricConfig(ClusterWith clientWith) {
		return new FabricConnectorConfig(clientWith);
	}

	@Override
	public Config getConfig() {
		return new ConfigBuilder()
				.withMasterUrl(super.clientWith.withMasterUrl())
				.withUsername(super.clientWith.withUsername())
				.withPassword(super.clientWith.withPassword())
				.withHttpsProxy(super.clientWith.withHttpsProxy())
				.build();
	}

}
