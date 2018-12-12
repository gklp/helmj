package org.helmj.sample;

import org.helmj.support.HelmJMultipleClient;
import org.helmj.support.config.ClusterWith;
import org.helmj.support.operation.OperationProvider;
import org.helmj.support.request.RequestListRelease;
import org.helmj.support.request.RequestVersion;
import org.helmj.support.response.ResponseReleaseList;
import org.helmj.support.response.ResponseVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HelmJSampleApplication {

	private final ClusterWith clusterWith = ClusterWith.builder()
			.withMasterUrl("cluster_api")
			.withHttpsProxy("proxy")
			.withUsername("admin")
			.withPassword("admin")
			.build();

	@Autowired
	private HelmJMultipleClient helmJClient;

	public static void main(String[] args) {
		SpringApplication.run(HelmJSampleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sampleVersion() {
		OperationProvider operationFactory = helmJClient.clusterWith(clusterWith);

		ResponseVersion response = operationFactory
				.versionOperation()
				.execute(RequestVersion.builder().build());

		if (response != null) {
			System.out.println(response.getSemVer());
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sampleList() {
		OperationProvider operationFactory = helmJClient.clusterWith(clusterWith);

		List<Integer> status = new ArrayList<>();
		status.add(RequestListRelease.RequestListStatus.DEPLOYED.getValue());

		RequestListRelease requestListRelease = RequestListRelease.builder().status(status).build();
		ResponseReleaseList releaseList = operationFactory.listOperation().execute(requestListRelease);
		if (releaseList != null && releaseList.getReleaseList() != null) {
			releaseList.getReleaseList().forEach(e -> {
				System.out.println(e.getName());
			});
		}
	}

}
