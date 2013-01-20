package com.xitxer.uateam.notification.core.parser.sitesource;

import java.net.SocketTimeoutException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class HttpSiteSource implements SiteSource {
	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";
	protected static final int TIMEOUT = 60000;

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
			} catch (Exception e) {
				e.fillInStackTrace();
				throw e;
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
