package com.xitxer.uateam.notification.server.parser.sitesource;

import java.net.SocketTimeoutException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpSiteSource implements SiteSource {
	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1) "
			+ "AppleWebKit/535.19 (KHTML, like Gecko) "
			+ "Chrome/18.0.1025.168 Safari/535.19";
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
				e.printStackTrace();
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

	@Override
	public Document getRootPage() throws Exception {
		return getDocument(baseUrl);
	}

	@Override
	public Document getSubPage(String subUrl) throws Exception {
		return getDocument(getUrl(subUrl));
	}
}
