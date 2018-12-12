package com.helmj.support.client.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Release {

	private String name;

	private Integer version;

	private String namespace;

	private String manifest;

}
