import com.helmj.support.HelmJMultipleClient;
import com.helmj.support.MultipleBlockingClientStub;
import com.helmj.support.config.ClientConfig;
import com.helmj.support.config.ClusterWith;
import com.helmj.connector.config.TillerConfig;
import com.helmj.support.operation.OperationProvider;
import com.helmj.support.request.RequestVersion;
import com.helmj.support.response.ResponseVersion;

import java.util.HashMap;

public class Sample {

	public static void main(String[] args) {
		HashMap<String, String> DEFAULT_LABELS = new HashMap<String, String>() {
			{
				put("name", "tiller");
				put("app", "helm");
			}
		};

		TillerConfig tillerConfig = TillerConfig.builder()
				.clientVersion("2.8.2")
				.namespace("kube-system")
				.port(44134)
				.labels(DEFAULT_LABELS)
				.build();

		ClientConfig clientConfig = ClientConfig.builder()
				.clientType("blocking-client")
				.connectorType("io.fabric8.kubernetes")
				.build();

		HelmJMultipleClient client = new MultipleBlockingClientStub(tillerConfig, clientConfig);


		ClusterWith clusterWith = ClusterWith.builder()
				.withHttpsProxy("proxy")
				.withMasterUrl("api_link")
				.withUsername("admin")
				.withPassword("admin")
				.build();

		OperationProvider operationFactory = client.clusterWith(clusterWith);

		ResponseVersion response = operationFactory.versionOperation().execute(RequestVersion.builder().build());
		System.out.println(response.getSemVer());

	}

}
