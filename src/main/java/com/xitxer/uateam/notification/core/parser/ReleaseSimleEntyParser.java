package com.xitxer.uateam.notification.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.xitxer.uateam.notification.core.model.ReleaseSimpleEntry;
import com.xitxer.uateam.notification.core.parser.exceptions.HtmlLayoutChangedException;
import com.xitxer.uateam.notification.core.parser.exceptions.message.ParseMessages;
import com.xitxer.uateam.notification.core.parser.sitesource.SiteSource;

public class ReleaseSimleEntyParser extends BaseParser {
	private static final String CSS_QUERY = "table.adminlist tr.row0, table.adminlist tr.row1";
	private static final String HTTP_PARAM_START = "?start=";
	private static final int START_FROM_DEFAULT = 0;
	private static final int START_FROM_STEP = 20;

	private final String url;
	private final int startFrom;

	private boolean parsed = false;
	private boolean wasResults = false;

	public ReleaseSimleEntyParser(final SiteSource siteSource, final String url) {
		this(siteSource, url, START_FROM_DEFAULT);
	}

	private ReleaseSimleEntyParser(final SiteSource siteSource, final String url, final int startFrom) {
		super(siteSource);
		this.url = url;
		this.startFrom = startFrom;
	}

	@Override
	protected Elements check(final Elements elements, final String errorMessage) throws HtmlLayoutChangedException {
		if(startFrom == START_FROM_DEFAULT){
			return super.check(elements, errorMessage);
		}else{
			return elements;
		}
	}

	@Override
	protected Document getSubPage(final String subUrl) throws Exception {
		return super.getSubPage(subUrl + HTTP_PARAM_START + startFrom);
	}

	public List<ReleaseSimpleEntry> get() throws Exception {
		List<ReleaseSimpleEntry> entries = new ArrayList<ReleaseSimpleEntry>();
		for (Element element : check(getSubPage(url).select(CSS_QUERY), ParseMessages.COULD_NOT_SELECT_WITH_CSS_QUERY + CSS_QUERY)) {
			try {
				Integer.parseInt(element.select(BaseParser.TAG_A).get(0).text());
				Elements elements = new Elements(Lists.asList(element.select(BaseParser.TAG_A).get(0), element.select(BaseParser.TAG_A).get(1),
						new Element[] {}));
				ReleaseSimpleEntry entry = new ReleaseSimpleEntry();
				entry.setName(elements.text());
				entry.setUrl(elements.get(0).attr(ATTR_HREF));
				entries.add(entry);
			} catch (Exception exception) {
				// eat this one
			}
		}
		parsed = true;
		wasResults = !entries.isEmpty();
		return entries;
	}

	public ReleaseSimleEntyParser nextParser() {
		if (parsed) {
			if (wasResults) {
				return new ReleaseSimleEntyParser(getSiteSource(), url, startFrom + START_FROM_STEP);
			} else {
				return null;
			}
		} else {
			throw new IllegalStateException("this parser has not parsed page yet");
		}
	}
}
