package com.helmj.support.client.operation;

@FunctionalInterface
public interface Operation<Req, Res> {

	Res execute(Req req);

}
