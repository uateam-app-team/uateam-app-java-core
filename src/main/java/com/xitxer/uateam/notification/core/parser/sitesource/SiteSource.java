package com.xitxer.uateam.notification.core.parser.sitesource;

import javax.annotation.Nullable;

import org.jsoup.nodes.Document;

public interface SiteSource {
	Document getRootPage() throws Exception;

	Document getSubPage(@Nullable String subUrl) throws Exception;
}
