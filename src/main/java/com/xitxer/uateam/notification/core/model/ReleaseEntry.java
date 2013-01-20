package com.xitxer.uateam.notification.core.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class ReleaseEntry implements Comparable<ReleaseEntry> {

	private String group;
	private String release;
	private long season;
	private long episode;

	private String groupLink;
	private String detailsLink;

	private String watchOnlineLink;
	private String iconLink;

	public ReleaseEntry() {
		super();
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public long getSeason() {
		return season;
	}

	public void setSeason(long season) {
		this.season = season;
	}

	public long getEpisode() {
		return episode;
	}

	public void setEpisode(long episode) {
		this.episode = episode;
	}

	public String getGroupLink() {
		return groupLink;
	}

	public void setGroupLink(String groupLink) {
		this.groupLink = groupLink;
	}

	public String getDetailsLink() {
		return detailsLink;
	}

	public void setDetailsLink(String detailsLink) {
		this.detailsLink = detailsLink;
	}

	public String getWatchOnlineLink() {
		return watchOnlineLink;
	}

	public void setWatchOnlineLink(String watchOnlineLink) {
		this.watchOnlineLink = watchOnlineLink;
	}

	public String getIconLink() {
		return iconLink;
	}

	public void setIconLink(String iconLink) {
		this.iconLink = iconLink;
	}

	public int compareTo(ReleaseEntry o) {
		if (this == o) {
			return 0;
		}
		if (o == null) {
			return 1;
		}
		return ComparisonChain.start().compare(group, o.group).compare(release, o.release).compare(season, o.season)
				.compare(episode, o.episode).compare(groupLink, o.groupLink).compare(detailsLink, o.detailsLink)
				.result();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReleaseEntry)) {
			return false;
		}
		ReleaseEntry that = (ReleaseEntry) obj;
		return Objects.equal(group, that.group) && Objects.equal(season, that.season)
				&& Objects.equal(release, that.release) && Objects.equal(episode, that.episode)
				&& Objects.equal(groupLink, that.groupLink) && Objects.equal(detailsLink, that.detailsLink);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(group, release, season, episode, groupLink, detailsLink);
	}

	@Override
	public String toString() {
		return "ReleaseEntry [group=" + group + ", release=" + release + ", season=" + season + ", episode=" + episode
				+ ", groupLink=" + groupLink + ", detailsLink=" + detailsLink + ", watchOnlineLink=" + watchOnlineLink
				+ ", iconLink=" + iconLink + "]";
	}
}
