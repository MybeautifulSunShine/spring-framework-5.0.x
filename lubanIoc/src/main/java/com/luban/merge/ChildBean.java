package com.luban.merge;

/**
 * 描述:
 *
 * @author HGJ
 * @version 1.0
 * @create 2020-12-20 14:42
 */
public class ChildBean {
	 String name;
	 String type;



	public void setName(String name) {
		this.name = name;
	}



	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChildBean{" +
				"name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
