package com.helmj.support;

import com.helmj.support.config.ClientWith;
import com.helmj.support.operation.OperationProvider;

public interface HelmJMultipleClient extends HelmJClient {

	OperationProvider withClient(ClientWith clientWith);

}
