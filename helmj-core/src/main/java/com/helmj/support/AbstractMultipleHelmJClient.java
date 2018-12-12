package com.helmj.support;

import com.helmj.HelmJClientException;
import com.helmj.connector.AbstractHelmJConnector;
import com.helmj.connector.ConnectorSupplier;
import com.helmj.support.config.ClientConfig;
import com.helmj.support.config.ClusterWith;
import com.helmj.ConfigAware;
import com.helmj.connector.config.TillerConfig;

import java.util.Arrays;
import java.util.Objects;

abstract class AbstractMultipleHelmJClient implements HelmJMultipleClient {

	private TillerConfig tillerConfig;

	private ClientConfig clientConfig;

	AbstractMultipleHelmJClient(ConfigAware... configs) {
		Arrays.asList(configs).forEach(c -> {
			if (c instanceof TillerConfig) {
				this.tillerConfig = (TillerConfig) c;
			}
			if (c instanceof ClientConfig) {
				this.clientConfig = (ClientConfig) c;
			}
		});

		if (this.tillerConfig == null) {
			throw new HelmJClientException("TillerConfig is missing.");
		}

		if (this.clientConfig == null) {
			throw new HelmJClientException("ClientConfig is missing.");
		}
	}

	HelmJTiller newImmutableClient(ClusterWith clusterWith) {
		Objects.requireNonNull(tillerConfig, "TillerConfig config is required.");
		Objects.requireNonNull(clientConfig, "ClientConfig config is required.");

		Objects.requireNonNull(clusterWith, "ClusterWith is required.");

		AbstractHelmJConnector connector = ConnectorSupplier.getFabricConnector(this.clientConfig);
		connector.setTillerConfig(this.tillerConfig);
		connector.setclusterWith(clusterWith);

		return new HelmJTiller(connector);
	}

}
