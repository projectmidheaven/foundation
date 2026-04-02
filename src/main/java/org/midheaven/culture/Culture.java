package org.midheaven.culture;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Maybe;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Parser;
import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@ValueClass
public final class Culture {
    
    private static final Pattern pattern = Pattern.compile("[a-z]{2}(-|_)?[A-Z]{0,2}");
    
    private static final Parser<Culture> PARSER  = new Parser<Culture>() {
        @Override
        protected boolean matchesExpectedPattern(String text) {
            return pattern.matcher(text).matches();
        }
        
        @Override
        protected String removeIllegalChars(String text) {
            return Strings.Transform.create().thenRemoveAllNumerics().thenRemoveAllSymbolsExcept('-', '_').apply(text);
        }
        
        @Override
        protected Culture wrap(String text) {
            var parts = Strings.Splitter.split(text).by("-|_");
            if (parts.count().isOne()){
                return new Culture(text, null);
            } else {
                return new Culture(parts.get(0), parts.get(1));
            }
        }
    };
    
    public static Culture parse(String code){
        return PARSER.parse(code);
    }

    public static Maybe<Culture> tryParse(String code){
        return PARSER.tryParse(code);
    }

    private final String languageIsoCode;
    private final String countryIsoCode;

    Culture(String languageIsoCode, String countryIsoCode){
        this.languageIsoCode = languageIsoCode;
        this.countryIsoCode = countryIsoCode;
    }

    public LanguageCode languageCode(){
        return new LanguageCode(languageIsoCode);
    }

    public Maybe<CountryCode> countryCode(){
        return Maybe.of(countryIsoCode).map(CountryCode::new);
    }

    public String toString(){
        if (countryIsoCode == null){
            return languageIsoCode;
        }
        return languageIsoCode + "_" + countryIsoCode;
    }

    public @Nullable Locale toLocale(){
        if (countryIsoCode == null){
            return Locale.of(languageIsoCode);
        }
        return Locale.of(languageIsoCode, countryIsoCode);
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Culture that
                && this.languageIsoCode.equals(that.languageIsoCode)
                && Objects.equals(this.countryIsoCode, that.countryIsoCode);
    }

    @Override
    public int hashCode(){
        return HashCode.asymmetric().add(this.languageIsoCode).add(this.countryIsoCode).hashCode();
    }
}
