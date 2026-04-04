package org.midheaven.culture;

import org.midheaven.collections.Array;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Parser;
import org.midheaven.lang.ValueClass;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents Language Code.
 */
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
    
    /**
     * Parses the provided value.
     * @param code the code value
     * @return the result of parse
     */
    public static LanguageCode parse(String code){
        return PARSER.parse(code);
    }

    /**
     * Performs all.
     * @return the result of all
     */
    public static Sequence<LanguageCode> all(){
        return Array.of(Locale.getISOLanguages()).map(LanguageCode::new);
    }

    private final String isoCode;

    LanguageCode(String isoCode){
        this.isoCode = isoCode;
    }

    /**
     * Checks whether iso Code.
     * @return the result of isoCode
     */
    @Nullable
    /**
     * Checks whether iso Code.
     * @return the result of isoCode
     */
    public String isoCode(){
        return isoCode;
    }

    /**
     * Returns to String.
     * @return the result of toString
     */
    @Nullable
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString(){
        return isoCode;
    }

    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public boolean equals(Object other){
        return other instanceof LanguageCode that
           && this.isoCode.equals(that.isoCode);
    }

    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    @Override
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode( ){
        return this.isoCode.hashCode();
    }

    /**
     * Performs in.
     * @param countryCode the countryCode value
     * @return the result of in
     */
    public @Nullable Culture in(CountryCode countryCode){
        Objects.requireNonNull(countryCode);
        return new Culture(this.isoCode, countryCode.isoCode());
    }

    /**
     * Performs culture.
     * @return the result of culture
     */
    public @Nullable Culture culture() {
        return new Culture(this.isoCode, null);
    }
}
