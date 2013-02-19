package com.xitxer.uateam.notification.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import com.xitxer.uateam.notification.core.model.GroupEntry;
import com.xitxer.uateam.notification.core.parser.exceptions.message.ParseMessages;
import com.xitxer.uateam.notification.core.parser.sitesource.SiteSource;

public class SeriesGroupsParser extends BaseParser {
	private static final String CSS_SELECT_QUERY = "div.module:first-child a.mainlevel";

	public SeriesGroupsParser(final SiteSource siteSource) {
		super(siteSource);
	}

	public List<GroupEntry> get() throws Exception {
		List<GroupEntry> entries = new ArrayList<GroupEntry>();
		for (Element element : check(getRootPage().select(CSS_SELECT_QUERY), ParseMessages.COULD_NOT_SELECT_WITH_CSS_QUERY + CSS_SELECT_QUERY)) {
			GroupEntry entry = new GroupEntry();
			entry.setName(element.text());
			entry.setLink(element.attr(ATTR_HREF));
			entries.add(entry);
		}
		return entries;
	}
}
