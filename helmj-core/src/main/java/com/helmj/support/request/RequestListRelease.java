package com.helmj.support.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class RequestListRelease {

	private List<Integer> status;

	public enum RequestListStatus {
		UNKNOWN(0),
		DEPLOYED(1),
		DELETED(2),
		SUPERSEDED(3),
		FAILED(4),
		DELETING(5),
		PENDING_INSTALL(6),
		PENDING_UPGRADE(7),
		PENDING_ROLLBACK(8),
		UNRECOGNIZED(-1);

		private int value;

		RequestListStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

}
