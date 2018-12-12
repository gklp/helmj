package com.helmj.support.operation;

import com.helmj.support.HelmJTiller;
import com.helmj.support.request.RequestDeleteRelease;
import com.helmj.support.request.RequestListRelease;
import com.helmj.support.request.RequestVersion;
import com.helmj.support.response.ResponseDeleteRelease;
import com.helmj.support.response.ResponseReleaseList;
import com.helmj.support.response.ResponseVersion;
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
