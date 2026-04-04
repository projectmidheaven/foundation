package org.midheaven.culture;

import org.midheaven.collections.Array;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Parser;
import org.midheaven.lang.ValueClass;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents Country Code.
 */
@ValueClass
public final class CountryCode {
    
    private static final Parser<CountryCode> PARSER = Parser.upperCode(CountryCode::new);
    
    /**
     * Parses the provided value.
     * @param code the code value
     * @return the result of parse
     */
    public static CountryCode parse(String code){
        return PARSER.parse(code);
    }

    /**
     * Performs all.
     * @return the result of all
     */
    public static Sequence<CountryCode> all(){
        return Array.of(Locale.getISOCountries()).map(CountryCode::new);
    }

    private final String isoCode;

    CountryCode(String isoCode){
        this.isoCode = isoCode;
    }

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
        return other instanceof CountryCode that
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
     * Performs speaking.
     * @param languageCode the languageCode value
     * @return the result of speaking
     */
    public @Nullable Culture speaking(LanguageCode languageCode){
        Objects.requireNonNull(languageCode);
        return new Culture(languageCode.isoCode(), this.isoCode);
    }
}
