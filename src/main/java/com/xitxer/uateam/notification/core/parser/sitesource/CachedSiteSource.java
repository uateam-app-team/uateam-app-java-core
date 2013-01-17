package com.xitxer.uateam.notification.core.parser.sitesource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jsoup.nodes.Document;

import com.google.common.base.Preconditions;

public class CachedSiteSource implements SiteSource {

	protected final Map<String, Document> cachedPages = new HashMap<String, Document>();
	private final SiteSource siteSource;

	public CachedSiteSource(@Nonnull final SiteSource siteSource) {
		super();
		this.siteSource = Preconditions.checkNotNull(siteSource);
	}

	protected Document cacheDocument(final String url, final Document document) {
		cachedPages.put(url, document);
		return document;
	}

	protected Document getCachedDocument(final String subUrl) {
		return cachedPages.get(subUrl);
	}

	protected Document getDocument(final String url) throws Exception {
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

	public Document getSubPage(@Nullable final String subUrl) throws Exception {
		return cacheDocument(subUrl, getDocument(subUrl));
	}
}
