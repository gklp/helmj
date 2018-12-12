package org.helmj.support;

import org.helmj.support.config.ClusterWith;
import org.helmj.support.operation.OperationProvider;

public interface HelmJMultipleClient extends HelmJClient {

	OperationProvider clusterWith(ClusterWith clusterWith);

}
