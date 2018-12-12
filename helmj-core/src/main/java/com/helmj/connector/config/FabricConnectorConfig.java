package com.helmj.connector.config;

import com.helmj.support.config.ClusterWith;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FabricConnectorConfig extends AbstractConnectorConfig<Config> {

	private FabricConnectorConfig(ClusterWith clusterWith) {
		super(clusterWith);
	}

	@Builder(builderMethodName = "fabricBuilder")
	public static FabricConnectorConfig newFabricConfig(ClusterWith clusterWith) {
		return new FabricConnectorConfig(clusterWith);
	}

	@Override
	public Config getConfig() {
		return new ConfigBuilder()
				.withMasterUrl(super.clusterWith.withMasterUrl())
				.withUsername(super.clusterWith.withUsername())
				.withPassword(super.clusterWith.withPassword())
				.withHttpsProxy(super.clusterWith.withHttpsProxy())
				.build();
	}

}
