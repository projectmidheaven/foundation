package org.midheaven.culture;

import org.midheaven.collections.Array;
import org.midheaven.collections.Sequence;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Parser;
import org.midheaven.lang.ValueClass;

import java.util.Locale;
import java.util.Objects;

@ValueClass
public final class CountryCode {
    
    private static final Parser<CountryCode> PARSER = Parser.upperCode(CountryCode::new);
    
    public static CountryCode parse(String code){
        return PARSER.parse(code);
    }

    public static Sequence<CountryCode> all(){
        return Array.of(Locale.getISOCountries()).map(CountryCode::new);
    }

    private final String isoCode;

    CountryCode(String isoCode){
        this.isoCode = isoCode;
    }

    public String isoCode(){
        return isoCode;
    }

    public String toString(){
        return isoCode;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof CountryCode that
           && this.isoCode.equals(that.isoCode);
    }

    @Override
    public int hashCode( ){
        return this.isoCode.hashCode();
    }

    public @Nullable Culture speaking(LanguageCode languageCode){
        Objects.requireNonNull(languageCode);
        return new Culture(languageCode.isoCode(), this.isoCode);
    }
}
