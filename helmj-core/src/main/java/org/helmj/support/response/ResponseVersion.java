package org.helmj.support.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseVersion {

	private String semVer;

	private String gitCommit;

	private String gitTreeState;

}
