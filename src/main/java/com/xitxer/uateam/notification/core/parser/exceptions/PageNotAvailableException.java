package com.xitxer.uateam.notification.core.parser.exceptions;

public class PageNotAvailableException extends Exception {
	private static final long serialVersionUID = 1614641508658943163L;

	public PageNotAvailableException(String string) {
		super(string);
	}

	public PageNotAvailableException(String string, Throwable throwable) {
		super(string, throwable);
	}
}
