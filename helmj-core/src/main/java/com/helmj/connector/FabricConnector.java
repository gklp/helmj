package com.helmj.connector;

import com.helmj.TillerException;
import com.helmj.connector.config.FabricConnectorConfig;
import com.helmj.connector.config.SocketForwarderConnection;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.LocalPortForward;
import io.fabric8.kubernetes.client.dsl.Listable;
import io.fabric8.kubernetes.client.dsl.base.OperationSupport;
import io.fabric8.kubernetes.client.dsl.internal.PortForwarderWebsocket;
import okhttp3.OkHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FabricConnector extends AbstractHelmJConnector<FabricConnectorConfig> {

	private final Log logger = LogFactory.getLog(getClass());

	private LocalPortForward forward;

	@Override
	public FabricConnectorConfig getConfiguration() {
		return FabricConnectorConfig.newFabricConfig(this.clientWith);
	}

	@Override
	public boolean isAlive() {
		return this.forward.isAlive();
	}

	@Override
	public void close() throws IOException {
		this.forward.close();
	}

	@Override
	public SocketForwarderConnection openSocketConnection() {
		DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient(getConfiguration().getConfig());
		try {
			final OkHttpClient httpClient = kubernetesClient.getHttpClient();
			logger.info("HelmJ is checking tiller pod is really exist on [" + tillerConfig.getNamespace() + "] ?");

			final Listable<PodList> pods = kubernetesClient.pods()
					.inNamespace(tillerConfig.getNamespace())
					.withLabels(tillerConfig.getLabels());

			if (pods != null) {
				String namespaceUrl = ((OperationSupport) pods).getNamespacedUrl().toExternalForm();

				logger.info("The url of api is calculated as [" + namespaceUrl + "]");

				if (namespaceUrl != null && !namespaceUrl.isEmpty()) {
					final List<Pod> podList = pods.list().getItems();
					return buildLocalPortForward(httpClient, namespaceUrl, podList);
				}
			}
		} catch (MalformedURLException e) {
			KubernetesClientException.launderThrowable(e);
		}
		return null;
	}

	private SocketForwarderConnection buildLocalPortForward(OkHttpClient httpClient, String namespaceUrl,
			List<Pod> podList) throws MalformedURLException {

		if (podList == null || podList.isEmpty()) {
			throw new TillerException("Helmj didn't find a tiller in kubernetes.");
		}
		String tillerName = podList.get(0).getMetadata().getName();

		logger.info("Pod is alive as [" + tillerName + "]");

		final URL url = new URL(ConnectorUtil.toConcatWith(namespaceUrl, tillerName, ConnectorUtil.SLASH));
		this.forward = new PortForwarderWebsocket(httpClient).forward(url, tillerConfig.getPort());

		logger.info("PortForwarderWebsocket is established.");

		return SocketForwarderConnection.builder().
				inetAddress(this.forward.getLocalAddress())
				.localPort(this.forward.getLocalPort())
				.build();
	}

}
