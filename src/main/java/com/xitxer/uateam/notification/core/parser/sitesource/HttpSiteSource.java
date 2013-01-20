package com.xitxer.uateam.notification.core.parser.sitesource;

import java.net.SocketTimeoutException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpSiteSource implements SiteSource {
	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";
	protected static final int TIMEOUT = 60000;

	protected String baseUrl;

	public HttpSiteSource(String url) {
		this.baseUrl = url;
	}

	private String getUrl(String subUrl) {
		return baseUrl + subUrl;
	}

	private Document getDocument(String url) throws Exception {
		Document document = null;
		long startTime = System.currentTimeMillis();
		while (document == null) {
			try {
				document = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
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

	public Document getRootPage() throws Exception {
		return getDocument(baseUrl);
	}

	public Document getSubPage(String subUrl) throws Exception {
		return getDocument(getUrl(subUrl));
	}
}
