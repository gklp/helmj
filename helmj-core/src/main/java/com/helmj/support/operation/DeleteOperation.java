package com.helmj.support.operation;

import com.helmj.support.request.RequestDeleteRelease;
import com.helmj.support.response.Release;
import com.helmj.support.response.ResponseDeleteRelease;
import hapi.release.ReleaseOuterClass;
import hapi.services.tiller.ReleaseServiceGrpc;
import hapi.services.tiller.Tiller;

public class DeleteOperation implements Operation<RequestDeleteRelease, ResponseDeleteRelease> {

	private ReleaseServiceGrpc.ReleaseServiceBlockingStub stub;

	DeleteOperation(ReleaseServiceGrpc.ReleaseServiceBlockingStub stub) {
		this.stub = stub;
	}

	@Override
	public ResponseDeleteRelease execute(RequestDeleteRelease requestDeleteRelease) {
		Tiller.UninstallReleaseRequest.Builder builder = Tiller.UninstallReleaseRequest.newBuilder();
		builder.setName(requestDeleteRelease.getReleaseName());
		Tiller.UninstallReleaseResponse uninstallReleaseResponse = stub.uninstallRelease(builder.build());
		if (uninstallReleaseResponse != null) {
			ReleaseOuterClass.Release release = uninstallReleaseResponse.getRelease();
			Release resRelease = Release.builder()
					.name(release.getName())
					.namespace(release.getNamespace())
					.manifest(release.getManifest())
					.build();
			return ResponseDeleteRelease.builder().release(resRelease).build();
		}
		return null;
	}
}