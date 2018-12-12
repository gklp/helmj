package org.helmj.connector.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Builder
@Getter
@Setter
public class SocketForwarderConnection {

	private InetAddress inetAddress;

	private int localPort;

}
