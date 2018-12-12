package com.helmj.support.operation;

import com.helmj.support.request.RequestVersion;
import com.helmj.support.response.ResponseVersion;
import hapi.services.tiller.ReleaseServiceGrpc;
import hapi.services.tiller.Tiller;
import hapi.version.VersionOuterClass;

class VersionOperation implements Operation<RequestVersion, ResponseVersion> {

	private ReleaseServiceGrpc.ReleaseServiceBlockingStub stub;

	VersionOperation(ReleaseServiceGrpc.ReleaseServiceBlockingStub stub) {
		this.stub = stub;
	}

	@Override
	public ResponseVersion execute(RequestVersion requestVersion) {
		Tiller.GetVersionRequest getVersionRequest = Tiller.GetVersionRequest.newBuilder().build();
		Tiller.GetVersionResponse getVersionResponse = stub.getVersion(getVersionRequest);
		if (getVersionResponse != null) {
			VersionOuterClass.Version version = getVersionResponse.getVersion();
			if (version != null) {
				return ResponseVersion.builder()
						.semVer(version.getSemVer())
						.gitCommit(version.getGitCommit())
						.gitTreeState(version.getGitTreeState()).build();
			}
		}
		return null;
	}

}
