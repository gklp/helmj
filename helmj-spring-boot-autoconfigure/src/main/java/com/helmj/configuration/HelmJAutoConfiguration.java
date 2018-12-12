package com.helmj.configuration;

import com.helmj.HelmJClientException;
import com.helmj.connector.config.TillerConfig;
import com.helmj.support.ClientType;
import com.helmj.support.HelmJMultipleClient;
import com.helmj.support.MultipleBlockingClientStub;
import com.helmj.support.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConditionalOnClass(HelmJMultipleClient.class)
@EnableConfigurationProperties({ HelmJAutoConfigurationClientProperties.class,
		HelmJAutoConfigurationTillerProperties.class })
public class HelmJAutoConfiguration {

	@Autowired
	private HelmJAutoConfigurationTillerProperties helmJAutoConfigurationTillerProperties;

	@Autowired
	private HelmJAutoConfigurationClientProperties helmJAutoConfigurationProviderProperties;

	@Bean
	@ConditionalOnMissingBean
	public TillerConfig helmjTillerConfig() {
		String namespace = helmJAutoConfigurationTillerProperties.getNamespace() == null
				? System.getProperty("tiller.namespace")
				: helmJAutoConfigurationTillerProperties.getNamespace();

		String clientVersion = helmJAutoConfigurationTillerProperties.getClientVersion() == null
				? System.getProperty("spring.helmj.tiller.client-version")
				: helmJAutoConfigurationTillerProperties.getClientVersion();

		//TODO: add map support for system properties
		Map<String, String> labels = helmJAutoConfigurationTillerProperties.getLabels();

		Integer port = helmJAutoConfigurationTillerProperties.getPort() == null
				? Integer.valueOf(System.getProperty("spring.helmj.tiller.port"))
				: helmJAutoConfigurationTillerProperties.getPort();

		return TillerConfig.builder()
				.namespace(namespace)
				.clientVersion(clientVersion)
				.port(port)
				.labels(labels)
				.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public ClientConfig helmjClientConfig() {
		String clientType = helmJAutoConfigurationProviderProperties.getClientType() == null
				? System.getProperty("spring.helmj.client.client-type")
				: helmJAutoConfigurationProviderProperties.getClientType();

		String connectorType = helmJAutoConfigurationProviderProperties.getConnectorType() == null
				? System.getProperty("spring.helmj.client.connector-type")
				: helmJAutoConfigurationProviderProperties.getConnectorType();

		return ClientConfig.builder()
				.connectorType(connectorType)
				.clientType(clientType)
				.build();
	}

	@Bean(name = "helmJMultipleClient")
	@ConditionalOnMissingBean
	public HelmJMultipleClient getHelmJClient(TillerConfig helmjTillerConfig, ClientConfig helmjClientConfig) {
		if (ClientType.MULTIPLE_BLOCKING_CLIENT.equals(ClientType.getType(helmjClientConfig.getClientType()))) {
			return new MultipleBlockingClientStub(helmjClientConfig, helmjTillerConfig);
		}
		throw new HelmJClientException("Unsupported client type : " + helmjClientConfig.getClientType());
	}

}
