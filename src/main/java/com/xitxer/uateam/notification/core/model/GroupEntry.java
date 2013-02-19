package com.xitxer.uateam.notification.core.model;

import com.google.common.base.Objects;

public class GroupEntry {
	private String name;
	private String link;

	public GroupEntry() {
		super();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GroupEntry)) {
			return false;
		}
		GroupEntry that = (GroupEntry) obj;
		return Objects.equal(name, that.name) && Objects.equal(link, that.link);
	}

	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, link);
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GroupEntry [name=" + name + ", link=" + link + "]";
	}
}
