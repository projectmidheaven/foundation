package org.midheaven.lang;

import java.util.LinkedList;
import java.util.List;

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
        return switch (a){
            case RaiseCaseCharTransformation r -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(rr);
                case LowerCaseCharTransformation ll -> List.of(ll);
                case TrimCharTransformation tt -> List.of(tt, r);
                case ReplaceCharTransformation pp -> List.of(r, pp);
                case ReplaceAllCharTransformation pp -> List.of(r, pp);
            };
            case LowerCaseCharTransformation low -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(rr);
                case LowerCaseCharTransformation ll -> List.of(ll);
                case TrimCharTransformation tt -> List.of(tt, low);
                case ReplaceCharTransformation pp -> List.of(low, pp);
                case ReplaceAllCharTransformation pp -> List.of(low, pp);
            };
            case TrimCharTransformation t -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(t, rr);
                case LowerCaseCharTransformation ll -> List.of(t, ll);
                case TrimCharTransformation tt -> List.of(t);
                case ReplaceCharTransformation pp -> List.of(t, pp);
                case ReplaceAllCharTransformation pp -> List.of(t, pp);
            };
            case ReplaceCharTransformation p -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(p, rr);
                case LowerCaseCharTransformation ll -> List.of(p, ll);
                case TrimCharTransformation tt -> (p.match() != ' ' && p.replace() != ' ') ? List.of(tt, p) : List.of(p, tt);
                case ReplaceCharTransformation pp -> List.of(p, pp);
                case ReplaceAllCharTransformation pp -> List.of(p, pp);
            };
            case ReplaceAllCharTransformation p -> switch (b) {
                case RaiseCaseCharTransformation rr -> List.of(p, rr);
                case LowerCaseCharTransformation ll -> List.of(p, ll);
                case TrimCharTransformation tt -> List.of(p, tt);
                case ReplaceCharTransformation pp -> List.of(p, pp);
                case ReplaceAllCharTransformation pp -> List.of(p, pp);
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
    public String apply(CharSequence text) {
        var current = text.toString();
        for (var t : chain){
            current = t.apply(current);
        }
        return current;
    }
}

sealed interface CharTransformation permits
    TrimCharTransformation,
    LowerCaseCharTransformation,
    RaiseCaseCharTransformation,
    ReplaceCharTransformation,
    ReplaceAllCharTransformation
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