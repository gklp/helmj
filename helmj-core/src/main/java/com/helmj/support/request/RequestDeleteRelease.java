package com.helmj.support.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RequestDeleteRelease {

	private String releaseName;

}
