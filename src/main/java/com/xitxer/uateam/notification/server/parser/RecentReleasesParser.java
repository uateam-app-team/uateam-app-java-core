package com.xitxer.uateam.notification.server.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChangedException;
import com.xitxer.uateam.notification.server.parser.exceptions.PageNotAvailableException;
import com.xitxer.uateam.notification.server.parser.sitesource.SiteSource;

public class RecentReleasesParser {

	private static final String QUERY_DIV_FRESHRELEASE = "div.freshrelease";
	private static final String QUERY_DIV_LINKS = "div.releasebuttons a[href]";
	private static final String QUERY_DIV_ONLINE_CODE = "div#online_code param[name=flashvars]";

	private static final String ATTR_HREF = "href";
	private static final String ATTR_SRC = "src";
	private static final String ATTR_VALUE = "value";

	private static final String SRC_IMG_HD = "/img/hd.jpg";
	private static final String SRC_IMG_DOWNLOAD = "/img/download.png";

	private static final String REGEXP_WATCH_ONLINE_FILE = ".*file\\=((http|https|ftp)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}/[a-zA-Z0-9\\-\\._/\\\\]+\\.mp4).*";

	private SiteSource siteSource;

	public RecentReleasesParser(SiteSource siteSource) {
		this.siteSource = siteSource;
	}

	private Elements check(Elements elements) throws HtmlLayoutChangedException {
		if (elements.isEmpty()) {
			throw new HtmlLayoutChangedException(
					"Could not select with css query - "
							+ QUERY_DIV_FRESHRELEASE);
		}
		return elements;
	}

	private Elements getContentElements() throws Exception {
		return check(siteSource.getRootPage().select(QUERY_DIV_FRESHRELEASE));
	}

	public List<ReleaseEntry> get() throws Exception {
		ReleaseEntryFiller filler = new ReleaseEntryFiller();
		List<ReleaseEntry> episodeEntries = new ArrayList<ReleaseEntry>();
		ReleaseEntry releaseEntry;
		for (Element element : getContentElements()) {
			if (filler.parse(element)) {
				releaseEntry = filler.refresh();
				episodeEntries.add(releaseEntry);
			}
		}
		return episodeEntries;
	}

	public void parseReleaseLinks(ReleaseEntry releaseEntry)
			throws PageNotAvailableException {
		try {
			Document document = siteSource.getSubPage(releaseEntry
					.getDetailsLink());
			Elements elements;
			elements = document.select(QUERY_DIV_ONLINE_CODE);
			if (elements.size() > 0) {
				String stringWithFilePath = elements.first().attr(ATTR_VALUE);
				if (stringWithFilePath != null) {
					Pattern pattern = Pattern.compile(REGEXP_WATCH_ONLINE_FILE);
					Matcher matcher = pattern.matcher(stringWithFilePath);
					if (matcher.find()) {
						releaseEntry.setWatchOnlineLink(matcher.group(1));
					}
				}
			}
			elements = document.select(QUERY_DIV_LINKS);
			if (elements.size() > 0) {
				Element elementDownload = null, elementDownloadHd = null;
				for (Element element : elements) {
					String atrrSrcValue = element.attr(ATTR_SRC);
					if (SRC_IMG_DOWNLOAD.equals(atrrSrcValue)) {
						elementDownload = element;
					}
					if (SRC_IMG_HD.equals(atrrSrcValue)) {
						elementDownloadHd = element;
					}
				}
				if (elementDownload != null) {
					releaseEntry
							.setTorrentLink(elementDownload.attr(ATTR_HREF));
				}
				if (elementDownloadHd != null) {
					releaseEntry.setTorrentHdLink(elementDownloadHd
							.attr(ATTR_HREF));
				}
			}
		} catch (Exception e) {
			PageNotAvailableException exception = new PageNotAvailableException(
					"Sub page not found or bad: "
							+ releaseEntry.getDetailsLink());
			exception.setStackTrace(e.getStackTrace());
			throw exception;
		}
	}
}
