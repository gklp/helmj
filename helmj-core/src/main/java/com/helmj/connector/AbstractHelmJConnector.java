package com.helmj.connector;

import com.helmj.connector.config.ConnectorConfig;
import com.helmj.support.config.TillerConfig;
import com.helmj.connector.config.SocketForwarderConnection;
import com.helmj.support.config.ClientWith;

import java.io.Closeable;

public abstract class AbstractHelmJConnector<C extends ConnectorConfig> implements Closeable {

	TillerConfig tillerConfig;

	ClientWith clientWith;

	public abstract SocketForwarderConnection openSocketConnection();

	public abstract C getConfiguration();

	public abstract boolean isAlive();

	public TillerConfig getTillerConfig() {
		return tillerConfig;
	}

	public void setTillerConfig(TillerConfig tillerConfig) {
		this.tillerConfig = tillerConfig;
	}

	public void setClientWith(ClientWith clientWith) {
		this.clientWith = clientWith;
	}
}
