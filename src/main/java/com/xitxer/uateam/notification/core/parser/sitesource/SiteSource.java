package com.xitxer.uateam.notification.core.parser.sitesource;

import org.jsoup.nodes.Document;

public interface SiteSource {
	Document getRootPage() throws Exception;

	Document getSubPage(String subpath) throws Exception;
}
