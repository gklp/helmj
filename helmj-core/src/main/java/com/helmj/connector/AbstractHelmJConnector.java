package com.helmj.connector;

import com.helmj.connector.config.AbstractConnectorConfig;
import com.helmj.connector.config.TillerConfig;
import com.helmj.connector.config.SocketForwarderConnection;
import com.helmj.support.config.ClusterWith;

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
