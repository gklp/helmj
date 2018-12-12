package org.helmj.support.operation;

@FunctionalInterface
public interface Operation<Req, Res> {

	Res execute(Req req);

}
