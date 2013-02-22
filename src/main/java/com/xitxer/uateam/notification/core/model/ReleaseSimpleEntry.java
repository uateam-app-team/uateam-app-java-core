package com.xitxer.uateam.notification.core.model;

public class ReleaseSimpleEntry {
	private String name;
	private String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ReleaseSimpleEntry [name=" + name + ", url=" + url + "]";
	}
}
