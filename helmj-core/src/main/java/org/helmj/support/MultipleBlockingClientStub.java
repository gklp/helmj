package org.helmj.support;

import org.helmj.ConfigAware;
import org.helmj.support.config.ClusterWith;
import org.helmj.support.operation.OperationProvider;

public class MultipleBlockingClientStub extends AbstractMultipleHelmJClient {

	public MultipleBlockingClientStub(ConfigAware... configs) {
		super(configs);
	}

	@Override
	public ClientType getClientType() {
		return ClientType.MULTIPLE_BLOCKING_CLIENT;
	}

	@Override
	public OperationProvider clusterWith(ClusterWith clusterWith) {
		return OperationProvider.newOperation(newImmutableClient(clusterWith));
	}

}
