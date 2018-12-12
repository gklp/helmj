package org.helmj.support.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ResponseReleaseList {

	private List<Release> releaseList;

}
