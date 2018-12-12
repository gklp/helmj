package com.helmj.connector;

import com.helmj.connector.config.AbstractConnectorConfig;
import com.helmj.connector.config.TillerConfig;
import com.helmj.connector.config.SocketForwarderConnection;
import com.helmj.support.config.ClusterWith;

import java.io.Closeable;

public abstract class AbstractHelmJConnector<C extends AbstractConnectorConfig> implements Closeable {

	TillerConfig tillerConfig;

	ClusterWith clientWith;

	public abstract SocketForwarderConnection openSocketConnection();

	public abstract C getConfiguration();

	public abstract boolean isAlive();

	public TillerConfig getTillerConfig() {
		return tillerConfig;
	}

	public void setTillerConfig(TillerConfig tillerConfig) {
		this.tillerConfig = tillerConfig;
	}

	public void setClientWith(ClusterWith clientWith) {
		this.clientWith = clientWith;
	}
}
