package com.xitxer.uateam.notification.core.parser.sitesource;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.google.common.base.Preconditions;

public class CachedSiteSource implements SiteSource {

	protected final Map<String, Document> cachedPages = new HashMap<String, Document>();
	private final SiteSource siteSource;

	public CachedSiteSource(SiteSource siteSource) {
		super();
		this.siteSource = Preconditions.checkNotNull(siteSource);
	}

	protected Document cacheDocument(String url, Document document) {
		cachedPages.put(url, document);
		return document;
	}

	protected Document getCachedDocument(String subUrl) {
		return cachedPages.get(subUrl);
	}

	protected Document getDocument(String url) throws Exception {
		Document document = getCachedDocument(url);
		if (document == null) {
			document = siteSource.getSubPage(url);
		}
		return document;
	}

	public CachedSiteSource clearCache() {
		cachedPages.clear();
		return this;
	}

	public Document getRootPage() throws Exception {
		return cacheDocument(null, getDocument(null));
	}

	public Document getSubPage(String subUrl) throws Exception {
		return cacheDocument(subUrl, getDocument(subUrl));
	}
}
