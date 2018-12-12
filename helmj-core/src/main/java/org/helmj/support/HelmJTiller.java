package org.helmj.support;

import hapi.services.tiller.ReleaseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.helmj.connector.AbstractHelmJConnector;
import org.helmj.connector.config.SocketForwarderConnection;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HelmJTiller {

	private AbstractHelmJConnector helmJConnector;

	public HelmJTiller(AbstractHelmJConnector helmJConnector) {
		Objects.requireNonNull(helmJConnector, "HelmJ connector is required.");
		this.helmJConnector = helmJConnector;
	}

	public ReleaseServiceGrpc.ReleaseServiceBlockingStub getReleaseServiceBlockingStub() {
		SocketForwarderConnection tillerConnectionDetail = this.helmJConnector.openConnection();
		if (tillerConnectionDetail != null) {
			String hostName = tillerConnectionDetail.getInetAddress().getHostName();
			int port = tillerConnectionDetail.getLocalPort();

			ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(hostName, port)
					.keepAliveTime(1, TimeUnit.SECONDS)
					.usePlaintext()
					.build();

			if (managedChannel != null && !managedChannel.isShutdown()) {
				ReleaseServiceGrpc.ReleaseServiceBlockingStub releaseServiceBlockingStub = ReleaseServiceGrpc
						.newBlockingStub(managedChannel);
				return MetadataUtils.attachHeaders(releaseServiceBlockingStub, getMetadata());
			}
		}
		return null;
	}

	private Metadata getMetadata() {
		Metadata metadata = new Metadata();
		metadata.put(Metadata.Key.of("x-helm-api-client", Metadata.ASCII_STRING_MARSHALLER),
				this.helmJConnector.getTillerConfig().getClientVersion());
		return metadata;
	}

}
