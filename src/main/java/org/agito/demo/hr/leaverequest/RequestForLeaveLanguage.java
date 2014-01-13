package org.agito.demo.hr.leaverequest;


import de.agito.cps.core.bpmo.api.enums.ILanguage;
import java.util.Locale;


/**
 * Languages for RequestForLeave.
 *
 * @author Jörg Burmeister
 */
public enum RequestForLeaveLanguage implements ILanguage {

	de("de", new Locale("de"), true),
	en("en", new Locale("en"), false);

	private RequestForLeaveLanguage(String code, Locale locale, boolean defaultIndicator) { this.code = code; this.locale = locale; this.defaultIndicator = defaultIndicator; }
	private String code;
	private boolean defaultIndicator;
	private Locale locale;
	public String getCode() { return code; }
	public boolean isDefault() { return defaultIndicator; }
	public Locale getLocale() { return locale; }

}

