package org.helmj.support.operation;

import lombok.Builder;
import org.helmj.support.HelmJTiller;
import org.helmj.support.request.RequestDeleteRelease;
import org.helmj.support.request.RequestListRelease;
import org.helmj.support.request.RequestVersion;
import org.helmj.support.response.ResponseDeleteRelease;
import org.helmj.support.response.ResponseReleaseList;
import org.helmj.support.response.ResponseVersion;

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
