package com.wrox.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@SuppressWarnings("unused")
public class InternationalizedException extends RuntimeException
        implements MessageSourceResolvable
{
    private static final long serialVersionUID = 1L;
    private static final Locale DEFAULT_LOCALE = Locale.US;

    private final String errorCode;
    private final String[] codes;
    private final Object[] arguments;

    public InternationalizedException(String errorCode, Object... arguments)
    {
        this(null, errorCode, null, arguments);
    }

    public InternationalizedException(Throwable cause, String errorCode,
                                      Object... arguments)
    {
        this(cause, errorCode, null, arguments);
    }

    public InternationalizedException(String errorCode, String defaultMessage,
                                      Object... arguments)
    {
        this(null, errorCode, defaultMessage, arguments);
    }

    public InternationalizedException(Throwable cause, String errorCode,
                                      String defaultMessage, Object... arguments)
    {
        super(defaultMessage == null ? errorCode : defaultMessage, cause);
        this.errorCode = errorCode;
        this.codes = new String[] { errorCode };
        this.arguments = arguments;
    }

    @Override
    public String getLocalizedMessage()
    {
        return this.errorCode;
    }

    public String getLocalizedMessage(MessageSource messageSource)
    {
        return this.getLocalizedMessage(messageSource, this.getLocale());
    }

    public String getLocalizedMessage(MessageSource messageSource, Locale locale)
    {
        return messageSource.getMessage(this, locale);
    }

    @Override
    public String[] getCodes()
    {
        return this.codes;
    }

    @Override
    public Object[] getArguments()
    {
        return this.arguments;
    }

    @Override
    public String getDefaultMessage()
    {
        return this.getMessage();
    }

    protected final Locale getLocale()
    {
        Locale locale = LocaleContextHolder.getLocale();
        return locale == null ? InternationalizedException.DEFAULT_LOCALE : locale;
    }
}
