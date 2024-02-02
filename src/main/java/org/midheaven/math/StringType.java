package org.midheaven.math;

enum StringType {

    SYMBOLIC(BLocks.SYMBOLIC),
    NUMERIC(BLocks.NUMERIC),
    ALPHANUMERIC(BLocks.NUMERIC + BLocks.ALPHABETIC),
    ALPHABETIC(BLocks.ALPHABETIC),
    ANY(BLocks.SYMBOLIC + BLocks.NUMERIC + BLocks.ALPHABETIC),
    ;

    interface BLocks {
        String ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyw";
        String NUMERIC = "01234567890";
        String SYMBOLIC = "!@#$%&*()-_+=[]{}~^?";
    }

    private final String block;

    StringType(String block) {
        this.block = block;
    }

    public String block() {
        return block;
    }
}
