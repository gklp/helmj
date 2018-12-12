package com.helmj.connector;

final public class ConnectorUtil {

	public final static String SLASH = "/";

	public static String toConcatWith(String s1, String s2, String with) {
		return s1.concat(with).concat(s2);
	}
}
