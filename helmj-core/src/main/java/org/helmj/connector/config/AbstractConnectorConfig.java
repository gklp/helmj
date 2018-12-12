package org.helmj.connector.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.helmj.HelmJClientException;
import org.helmj.support.config.ClusterWith;

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
