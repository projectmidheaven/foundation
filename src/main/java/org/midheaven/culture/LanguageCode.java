package org.midheaven.culture;

import org.midheaven.collections.Array;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Parser;
import org.midheaven.lang.ValueClass;

import java.util.Locale;
import java.util.Objects;

@ValueClass
public final class LanguageCode {
    
    // top 10 most spoke languages
    // according to https://www.babbel.com/en/magazine/the-10-most-spoken-languages-in-the-world
    /**
     * Arabic
     */
    public static final LanguageCode AR = new LanguageCode("ar");
    /**
     * Bengali
     */
    public static final LanguageCode BN = new LanguageCode("bn");
    /**
     * English
     */
    public static final LanguageCode EN = new LanguageCode("en");
    /**
     * Spanish
     */
    public static final LanguageCode ES = new LanguageCode("es");
    /**
     * Hindi
     */
    public static final LanguageCode HI = new LanguageCode("hi");
    /**
     * Japanese
     */
    public static final LanguageCode JA = new LanguageCode("ja");
    /**
     * Punjabi
     */
    public static final LanguageCode PA = new LanguageCode("pa");
    /**
     * Portuguese
     */
    public static final LanguageCode PT = new LanguageCode("pt");
    /**
     * Russian
     */
    public static final LanguageCode RU = new LanguageCode("ru");
    /**
     * Chinese
     */
    public static final LanguageCode ZH = new LanguageCode("zh");

    private static final Parser<LanguageCode> PARSER = Parser.lowerCode(LanguageCode::new);
    
    public static LanguageCode parse(String code){
        return PARSER.parse(code);
    }

    public static Sequence<LanguageCode> all(){
        return Array.of(Locale.getISOLanguages()).map(LanguageCode::new);
    }

    private final String isoCode;

    LanguageCode(String isoCode){
        this.isoCode = isoCode;
    }

    @Nullable
    public String isoCode(){
        return isoCode;
    }

    @Nullable
    public String toString(){
        return isoCode;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof LanguageCode that
           && this.isoCode.equals(that.isoCode);
    }

    @Override
    public int hashCode( ){
        return this.isoCode.hashCode();
    }

    public @Nullable Culture in(CountryCode countryCode){
        Objects.requireNonNull(countryCode);
        return new Culture(this.isoCode, countryCode.isoCode());
    }

    public @Nullable Culture culture() {
        return new Culture(this.isoCode, null);
    }
}
