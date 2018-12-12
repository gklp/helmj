package com.helmj.support;

import com.helmj.support.config.ClusterWith;
import com.helmj.support.operation.OperationProvider;

public interface HelmJMultipleClient extends HelmJClient {

	OperationProvider clusterWith(ClusterWith clusterWith);
}
