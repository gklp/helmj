package com.helmj.support.client.operation;

import com.helmj.support.client.model.request.RequestListRelease;
import com.helmj.support.client.model.response.Release;
import com.helmj.support.client.model.response.ResponseReleaseList;
import hapi.release.ReleaseOuterClass;
import hapi.services.tiller.ReleaseServiceGrpc;
import hapi.services.tiller.Tiller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListOperation implements Operation<RequestListRelease, ResponseReleaseList> {

	private ReleaseServiceGrpc.ReleaseServiceBlockingStub stub;

	ListOperation(ReleaseServiceGrpc.ReleaseServiceBlockingStub stub) {
		this.stub = stub;
	}

	@Override
	public ResponseReleaseList execute(RequestListRelease requestListRelease) {
		Tiller.ListReleasesRequest.Builder listRequestBuilder = Tiller.ListReleasesRequest.newBuilder();
		listRequestBuilder.addAllStatusCodesValue(requestListRelease.getStatus());
		Tiller.ListReleasesRequest listReleasesRequest = listRequestBuilder.build();

		Iterator<Tiller.ListReleasesResponse> listReleasesResponseIterator = stub.listReleases(listReleasesRequest);
		final Tiller.ListReleasesResponse response = listReleasesResponseIterator.next();

		List<Release> result = null;
		if (response != null && response.getReleasesList() != null && !response.getReleasesList().isEmpty()) {
			List<ReleaseOuterClass.Release> releasesList = response.getReleasesList();
			result = new ArrayList<>();
			for (ReleaseOuterClass.Release item : releasesList) {
				result.add(Release.builder()
						.name(item.getName())
						.namespace(item.getNamespace())
						.manifest(item.getManifest())
						.build());
			}
		}
		return ResponseReleaseList.builder().releaseList(result).build();
	}
}