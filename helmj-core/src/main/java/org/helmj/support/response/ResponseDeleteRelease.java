package org.helmj.support.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDeleteRelease {

	private Release release;

}
