package com.helmj.connector;

import com.helmj.HelmJConnectorException;
import com.helmj.HelmJTillerException;
import com.helmj.connector.config.FabricConnectorConfig;
import com.helmj.connector.config.SocketForwarderConnection;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.LocalPortForward;
import io.fabric8.kubernetes.client.dsl.Listable;
import io.fabric8.kubernetes.client.dsl.base.OperationSupport;
import io.fabric8.kubernetes.client.dsl.internal.PortForwarderWebsocket;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
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
	public SocketForwarderConnection openConnection() {
		DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient(getConfiguration().getConfig());
		try {
			final OkHttpClient httpClient = kubernetesClient.getHttpClient();
			logger.info("HelmJ is checking tiller pod is really exist on [" + tillerConfig.getNamespace() + "] ?");

			final Listable<PodList> pods = kubernetesClient.pods()
					.inNamespace(tillerConfig.getNamespace())
					.withLabels(tillerConfig.getLabels());

			if (pods != null) {
				String clusterApiUrl = ((OperationSupport) pods).getNamespacedUrl().toExternalForm();

				logger.info("The url of api : [" + clusterApiUrl + "]");

				if (clusterApiUrl != null) {
					final List<Pod> podList = pods.list().getItems();
					if (CollectionUtils.isNotEmpty(podList)) {
						String tillerPodName = podList.get(0).getMetadata().getName();

						String urlBuilder = clusterApiUrl
								+ "/"
								+ tillerPodName;

						logger.info("Pod is alive as [" + tillerPodName + "]");

						final URL url = new URL(urlBuilder);
						this.forward = new PortForwarderWebsocket(httpClient).forward(url, tillerConfig.getPort());

						return SocketForwarderConnection.builder().
								inetAddress(this.forward.getLocalAddress())
								.localPort(this.forward.getLocalPort())
								.build();
					}
					throw new HelmJTillerException("Helmj didn't find a tiller in kubernetes.");
				}
			}
		} catch (MalformedURLException e) {
			throw new HelmJConnectorException("An error has occurred." + e.getMessage());
		}
		return null;
	}

}
