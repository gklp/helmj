package com.helmj.sample;

import com.helmj.support.HelmJMultipleClient;
import com.helmj.support.config.ClientWith;
import com.helmj.support.request.RequestListRelease;
import com.helmj.support.request.RequestVersion;
import com.helmj.support.response.ResponseReleaseList;
import com.helmj.support.response.ResponseVersion;
import com.helmj.support.operation.OperationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HelmjSampleApplication {

	private final ClientWith clientWith = ClientWith.builder()
			.withHttpsProxy("proxy")
			.withUsername("admin")
			.withPassword("admin")
			.build();

	@Autowired
	private HelmJMultipleClient helmJClient;

	public static void main(String[] args) {
		SpringApplication.run(HelmjSampleApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sampleVersion() throws IOException {
		OperationProvider operationFactory = helmJClient.withClient(clientWith);

		ResponseVersion response = operationFactory
				.versionOperation()
				.execute(RequestVersion.builder().build());

		helmJClient.getConnector().close();

		if (response != null) {
			System.out.println(response.getSemVer());
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sampleList() {
		OperationProvider operationFactory = helmJClient.withClient(clientWith);

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
