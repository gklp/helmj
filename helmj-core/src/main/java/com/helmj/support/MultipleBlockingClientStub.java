package com.helmj.support;

import com.helmj.support.config.ClientWith;
import com.helmj.support.config.ConfigAware;
import com.helmj.support.operation.OperationProvider;

public class MultipleBlockingClientStub extends AbstractMultipleHelmJClient {

	public MultipleBlockingClientStub(ConfigAware... configs) {
		super(configs);
	}

	@Override
	public ClientType getClientType() {
		return ClientType.BLOCKING_CLIENT;
	}

	@Override
	public OperationProvider withClient(ClientWith clientWith) {
		return OperationProvider.newOperation(newImmutableClient(clientWith));
	}
}
