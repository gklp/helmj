package com.helmj.connector.config;

import com.helmj.HelmJClientException;
import com.helmj.support.config.ClusterWith;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractConnectorConfig<T> {

	final protected ClusterWith clusterWith;
	
	private final Log logger = LogFactory.getLog(getClass());

	AbstractConnectorConfig(ClusterWith clusterWith) {
		if (clusterWith.withMasterUrl() == null) {
			throw new HelmJClientException("Master url is required.");
		}

		if (clusterWith.withUsername() == null) {
			throw new HelmJClientException("Username is required.");
		}

		if (clusterWith.withPassword() == null) {
			throw new HelmJClientException("Password is required.");
		}

		logger.info("Helmj will try to connect : [" + clusterWith.withMasterUrl() + "]");
		this.clusterWith = clusterWith;
	}

	public abstract T getConfig();

}
