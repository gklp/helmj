package com.helmj.support.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Setter
@Accessors(fluent = true)
public class ClusterWith {

	private String withMasterUrl;

	private String withUsername;

	private String withPassword;

	private String withHttpsProxy;

}
