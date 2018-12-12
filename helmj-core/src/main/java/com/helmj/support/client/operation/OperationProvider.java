package com.helmj.support.client.operation;

import com.helmj.support.HelmJTiller;
import com.helmj.support.client.model.request.RequestDeleteRelease;
import com.helmj.support.client.model.request.RequestListRelease;
import com.helmj.support.client.model.request.RequestVersion;
import com.helmj.support.client.model.response.ResponseDeleteRelease;
import com.helmj.support.client.model.response.ResponseReleaseList;
import com.helmj.support.client.model.response.ResponseVersion;
import lombok.Builder;

public class OperationProvider {

	private HelmJTiller helmJTiller;

	private OperationProvider(HelmJTiller helmJTiller) {
		this.helmJTiller = helmJTiller;
	}

	@Builder(builderMethodName = "operationBuilder")
	public static OperationProvider newOperation(HelmJTiller helmJTiller) {
		return new OperationProvider(helmJTiller);
	}

	public Operation<RequestVersion, ResponseVersion> versionOperation() {
		return new VersionOperation(helmJTiller.getReleaseServiceBlockingStub());
	}

	public Operation<RequestListRelease, ResponseReleaseList> listOperation() {
		return new ListOperation(helmJTiller.getReleaseServiceBlockingStub());
	}

	public Operation<RequestDeleteRelease, ResponseDeleteRelease> deleteOperation() {
		return new DeleteOperation(helmJTiller.getReleaseServiceBlockingStub());
	}

}
