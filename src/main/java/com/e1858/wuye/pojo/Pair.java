package com.e1858.wuye.pojo;

public class Pair<First, Second> {
	private final First first;
	private final Second second;

	public Pair(First first, Second second) {
		super();
		this.first = first;
		this.second = second;
	}

	public First getFirst() {
		return first;
	}

	public Second getSecond() {
		return second;
	}

}
