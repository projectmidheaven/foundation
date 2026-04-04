package org.midheaven.lang;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;

/**
 * Builder for Chain String Transform instances.
 */
class ChainStringTransformBuilder implements StringTransformBuilder{
    
    private final List<CharTransformation> chain = new LinkedList<>();
    
    @Override
    public StringTransformBuilder thenReplace(char match, char newChar) {
        add(new ReplaceCharTransformation(match, newChar));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenReplaceAll(CharSequence match, CharSequence replacement) {
        add(new ReplaceAllCharTransformation(match.toString(), replacement.toString()));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenTrim() {
        add(TrimCharTransformation.INSTANCE);
        return this;
    }
    
    private void add(CharTransformation op) {
        if (chain.isEmpty()){
            chain.add(op);
            return;
        }
        
        var newChain = new LinkedList<CharTransformation>();
        CharTransformation b = op;
        CharTransformation a;
        while(!chain.isEmpty()){
            a = chain.removeLast();
            var res = cross(a,b);
            if (res.size() > 1) {
                newChain.addFirst(res.getLast());
            }
            b = res.getFirst();
        }
        newChain.addFirst(b);
       
        this.chain.addAll(newChain);
    }
    
    private List<CharTransformation> cross(CharTransformation a, CharTransformation b) {
        var original = List.of(a, b);
        var inverted = List.of(b, a);
        return switch (a){
            
            case LowerCaseCharTransformation low -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(rr);
                case LowerCaseCharTransformation ll -> List.of(ll);
                case TrimCharTransformation ignored -> inverted;
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
            };
            case RaiseCaseCharTransformation r -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(rr);
                case LowerCaseCharTransformation ll -> List.of(ll);
                case TrimCharTransformation ignored -> inverted;
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
            };
            case TrimCharTransformation t -> switch (b) {
                case RaiseCaseCharTransformation ignored -> original;
                case LowerCaseCharTransformation ignored -> original;
                case TrimCharTransformation ignored -> List.of(t);
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
            };
            case ReplaceCharTransformation p -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(p, rr);
                case LowerCaseCharTransformation ll -> List.of(p, ll);
                case TrimCharTransformation tt -> (p.match() != ' ' && p.replace() != ' ') ? List.of(tt, p) : List.of(p, tt);
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
                
            };
            case ReplaceAllCharTransformation p -> switch (b) {
                case RaiseCaseCharTransformation rr ->original;
                case LowerCaseCharTransformation ll -> original;
                case TrimCharTransformation ignored -> original;
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
            };
            case RetainPredicateCharTransformation(IntPredicate r) -> switch (b) {
                case RaiseCaseCharTransformation rr ->original;
                case LowerCaseCharTransformation ll -> original;
                case TrimCharTransformation ignored -> original;
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RemovePredicateCharTransformation ignored -> original;
                case RetainPredicateCharTransformation (IntPredicate p) -> List.of(new RetainPredicateCharTransformation(r.or(p)));
            };
            case RemovePredicateCharTransformation(IntPredicate r) -> switch (b) {
                case RaiseCaseCharTransformation rr ->original;
                case LowerCaseCharTransformation ll -> original;
                case TrimCharTransformation ignored -> original;
                case ReplaceCharTransformation ignored -> original;
                case ReplaceAllCharTransformation ignored -> original;
                case RetainPredicateCharTransformation ignored -> original;
                case RemovePredicateCharTransformation (IntPredicate p) -> List.of(new RemovePredicateCharTransformation(r.and(p)));
            };
        };
    }
    
    @Override
    public StringTransformBuilder thenLowerCase() {
        add(LowerCaseCharTransformation.INSTANCE);
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRaiseCase() {
        add(RaiseCaseCharTransformation.INSTANCE);
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRemoveAllNumerics() {
        add(new RemovePredicateCharTransformation(Character::isDigit));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRetainNumericsOnly() {
        add(new RetainPredicateCharTransformation(Character::isDigit));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRemoveAllAlphabetic() {
        add(new RemovePredicateCharTransformation(Character::isAlphabetic));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRetainAlphabeticOnly() {
        add(new RetainPredicateCharTransformation(Character::isAlphabetic));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRemoveAllSymbols() {
        add(new RemovePredicateCharTransformation(c -> c != ' ' && !Character.isLetterOrDigit(c)));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRemoveAllSymbolsExcept(char... symbols) {
        Set<Character> set = new HashSet<>();
        set.add(' ');
        for (var c : symbols){
            set.add(c);
        }
        add(new RemovePredicateCharTransformation(c -> !set.contains((char)c) &&  !Character.isLetterOrDigit(c)));
        return this;
    }
    
    @Override
    public StringTransformBuilder thenRetainSymbolsOnly() {
        add(new RetainPredicateCharTransformation(c -> !Character.isLetterOrDigit(c)));
        return this;
    }
    
    @Override
    public String apply(CharSequence text) {
        var current = text.toString();
        for (var t : chain){
            current = t.apply(current);
        }
        return current;
    }
}

sealed interface CharTransformation permits
        LowerCaseCharTransformation,
        RaiseCaseCharTransformation,
        RemovePredicateCharTransformation,
        ReplaceAllCharTransformation,
        ReplaceCharTransformation,
        RetainPredicateCharTransformation,
        TrimCharTransformation
{
    
    String apply(String original);
}

final class TrimCharTransformation implements CharTransformation {
    
    static final TrimCharTransformation INSTANCE = new TrimCharTransformation();
    
    @Override
    public String apply(String original) {
        return original.trim();
    }
}

final class LowerCaseCharTransformation implements CharTransformation {
    
    static final LowerCaseCharTransformation INSTANCE = new LowerCaseCharTransformation();
    
    @Override
    public String apply(String original) {
        return original.toLowerCase();
    }
}

final class RaiseCaseCharTransformation implements CharTransformation {
    
    static final RaiseCaseCharTransformation INSTANCE = new RaiseCaseCharTransformation();
    
    @Override
    public String apply(String original) {
        return original.toUpperCase();
    }
}

record ReplaceCharTransformation(char match, char replace) implements CharTransformation {
    
    @Override
    public String apply(String original) {
        return original.replace(match, replace);
    }
}

record ReplaceAllCharTransformation(String match, String replace) implements CharTransformation {
    
    @Override
    public String apply(String original) {
        return original.replaceAll(match, replace);
    }
}


record RetainPredicateCharTransformation(IntPredicate predicate) implements CharTransformation {
    
    @Override
    public String apply(String original) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < original.length(); i++){
            char c = original.charAt(i);
            if(predicate.test(c)){
                builder.append(c);
            }
        }
        
        return builder.toString();
    }
}

record RemovePredicateCharTransformation(IntPredicate predicate) implements CharTransformation {
    @Override
    public String apply(String original) {
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < original.length(); i++){
            char c = original.charAt(i);
            if(!predicate.test(c)){
                builder.append(c);
            }
        }
        
        return builder.toString();
    }
}
