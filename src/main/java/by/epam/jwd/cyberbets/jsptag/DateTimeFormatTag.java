package by.epam.jwd.cyberbets.jsptag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static org.apache.taglibs.standard.tag.common.core.Util.getScope;
import static org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport.parseLocale;


public class DateTimeFormatTag extends TagSupport {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeFormatTag.class);

    private Object value;
    private String pattern;
    private String style;
    private ZoneId zoneId;
    private Locale locale;
    private String var;
    private int scope;

    public DateTimeFormatTag() {
        super();
        init();
    }

    private void init() {
        var = null;
        value = null;
        pattern = null;
        style = null;
        zoneId = null;
        locale = null;
        scope = PageContext.PAGE_SCOPE;
    }

    public void release() {
        init();
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
        this.scope = getScope(scope);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Sets the zone attribute.
     *
     * @param dtz  the zone
     * @throws JspTagException incorrect zone or dtz parameter
     */
    public void setZoneId(Object dtz) throws JspTagException {
        if (dtz == null || (dtz instanceof String && ((String) dtz).isEmpty())) {
            this.zoneId = null;
        } else if (dtz instanceof ZoneId) {
            this.zoneId = (ZoneId) dtz;
        } else if (dtz instanceof String) {
            try {
                this.zoneId = ZoneId.of((String) dtz);
            } catch (IllegalArgumentException iae) {
                throw new JspTagException("Incorrect Zone: " + dtz);
            }
        } else
            throw new JspTagException("Can only accept ZoneId or String objects.");
    }

    /**
     * Sets the style attribute.
     *
     * @param locale  the locale
     * @throws JspTagException parameter not a Locale or String
     */
    public void setLocale(Object locale) throws JspTagException {
        if (locale == null) {
            this.locale = null;
        } else if (locale instanceof Locale) {
            this.locale = (Locale) locale;
        } else if (locale instanceof String) {
            this.locale = parseLocale((String) locale);
        } else
            throw new JspTagException("Can only accept Locale or String objects.");
    }

    public int doEndTag() throws JspException {
        if (value == null) {
            if (var != null) {
                pageContext.removeAttribute(var, scope);
            }
            return EVAL_PAGE;
        }

        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofPattern(pattern);

        Locale locale = this.locale;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (locale != null) {
            formatter = formatter.withLocale(locale);
        }

        ZoneId zoneId = this.zoneId;
        if (zoneId != null) {
            formatter = formatter.withZone(zoneId);
        } else {
            if (value instanceof Instant
                    || value instanceof LocalDateTime
                    || value instanceof OffsetDateTime
                    || value instanceof OffsetTime
                    || value instanceof LocalTime) {
                formatter = formatter.withZone(ZoneId.systemDefault());
            }
        }

        String formatted;
        if (value instanceof TemporalAccessor) {
            formatted = formatter.format((TemporalAccessor) value);
        } else {
            throw new JspException(
                    "Value attribute of format tag must be a TemporalAccessor," +
                            " was: " + value.getClass().getName());
        }

        if (var != null) {
            pageContext.setAttribute(var, formatted, scope);
        } else {
            try {
                pageContext.getOut().print(formatted);
            } catch (IOException ioe) {
                throw new JspTagException(ioe.toString(), ioe);
            }
        }
        return EVAL_PAGE;
    }
}