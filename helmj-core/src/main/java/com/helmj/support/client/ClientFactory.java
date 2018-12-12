package com.helmj.support.client;

import com.helmj.HelmjClientException;
import com.helmj.support.HelmJMultipleClient;
import com.helmj.support.client.config.ClientConfig;
import com.helmj.support.client.types.ClientType;

public class ClientFactory {

	public static HelmJMultipleClient getClient(TillerConfig tillerConfig, ClientConfig clientConfig) {
		if (ClientType.BLOCKING_CLIENT.equals(ClientType.getType(clientConfig.getClientType()))) {
			return new BlockingClient(tillerConfig, clientConfig);
		}
		throw new HelmjClientException("Unsupported client type : " + clientConfig.getClientType());
	}
}
