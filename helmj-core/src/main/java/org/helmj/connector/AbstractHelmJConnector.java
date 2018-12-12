package org.helmj.connector;

import org.helmj.connector.config.AbstractConnectorConfig;
import org.helmj.connector.config.SocketForwarderConnection;
import org.helmj.connector.config.TillerConfig;
import org.helmj.support.config.ClusterWith;

import java.io.Closeable;

public abstract class AbstractHelmJConnector<C extends AbstractConnectorConfig> implements Closeable {

	TillerConfig tillerConfig;

	ClusterWith clusterWith;

	public abstract SocketForwarderConnection openConnection();

	public abstract C getConfiguration();

	public abstract boolean isAlive();

	public TillerConfig getTillerConfig() {
		return tillerConfig;
	}

	public void setTillerConfig(TillerConfig tillerConfig) {
		this.tillerConfig = tillerConfig;
	}

	public void setclusterWith(ClusterWith clusterWith) {
		this.clusterWith = clusterWith;
	}

}
