package com.xitxer.uateam.notification.core.parser.sitesource;

import java.net.SocketTimeoutException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class HttpSiteSource implements SiteSource {
	private static final int TIMEOUT = 60000;

	protected String baseUrl;

	public HttpSiteSource(final String url) {
		super();
		baseUrl = Preconditions.checkNotNull(url);
	}

	protected Document getDocument(final String subUrl) throws Exception {
		String fullUrl = getUrl(subUrl);
		Document document = null;
		long startTime = System.currentTimeMillis();
		while (document == null) {
			try {
				document = Jsoup.parse(new URL(fullUrl).openStream(), "utf-8", fullUrl);
			} catch (SocketTimeoutException e) {
				if (System.currentTimeMillis() - startTime > TIMEOUT) {
					e.fillInStackTrace();
					throw e;
				}
			}
		}
		return document;
	}

	protected String getUrl(final String subUrl) {
		return baseUrl + Strings.nullToEmpty(subUrl);
	}

	public Document getRootPage() throws Exception {
		return getDocument(null);
	}

	public Document getSubPage(final String subUrl) throws Exception {
		return getDocument(subUrl);
	}
}
