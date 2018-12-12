package com.helmj.connector;

import com.helmj.HelmJConnectorException;
import com.helmj.support.config.ClientConfig;

import java.util.function.Supplier;

public class ConnectorSupplier<T extends AbstractHelmJConnector> {

	private Supplier<T> connectorSupplier;

	private ConnectorSupplier(Supplier<T> connectorSupplier) {
		this.connectorSupplier = connectorSupplier;
	}

	public static AbstractHelmJConnector getFabricConnector(ClientConfig clientConfig) {
		if ("fabric".equalsIgnoreCase(clientConfig.getConnectorType())) {
			ConnectorSupplier<FabricConnector> fabricConnectorSupplier = new ConnectorSupplier<>(FabricConnector::new);
			return fabricConnectorSupplier.getConnectorSupplier();
		}
		throw new HelmJConnectorException("Unsupported client type : " + clientConfig.getConnectorType());
	}

	private T getConnectorSupplier() {
		return connectorSupplier.get();
	}

}

