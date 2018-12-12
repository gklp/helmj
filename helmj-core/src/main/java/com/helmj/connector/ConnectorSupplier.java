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
		if ("io.fabric8.kubernetes".equalsIgnoreCase(clientConfig.getConnectorType())) {
			ConnectorSupplier<FabricConnector> fabricConnectorSupplier = new ConnectorSupplier<>(FabricConnector::new);
			return fabricConnectorSupplier.getConnectorSupplier();
		}
		throw new HelmJConnectorException("Unsupported connector type : " + clientConfig.getConnectorType());
	}

	private T getConnectorSupplier() {
		return connectorSupplier.get();
	}

}

