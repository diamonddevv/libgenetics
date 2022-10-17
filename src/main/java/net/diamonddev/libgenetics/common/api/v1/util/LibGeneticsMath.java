package net.diamonddev.libgenetics.common.api.v1.util;

import java.util.ArrayList;
import java.util.Collection;

public class LibGeneticsMath {
    /**
     * Checks if the number after the decimal point is only zeroes, i.e. has a decimal.
     * @param number Any number.
     * @return True if the number does not have only zeroes after the decimal point.
     */
    public static boolean hasDecimal(double number) {
        return number % 1 == 0;
    }

    public static Collection<Integer> getEachIntegerRange(int origin, int bound) {
        Collection<Integer> intCol = new ArrayList<>();
        for (int i = origin; i <= bound; i++) {
            intCol.add(i);
        }
        return intCol;
    }
}
