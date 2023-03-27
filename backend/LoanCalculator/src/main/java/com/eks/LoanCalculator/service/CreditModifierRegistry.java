package com.eks.LoanCalculator.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * This class represents a registry for credit modifiers, which are used to
 * calculate
 * 
 * the interest rate for loans based on the credit score of the borrower.
 * 
 * The class is implemented as a singleton and contains a static map that maps
 * the credit score
 * 
 * to a specific credit modifier.
 */
public class CreditModifierRegistry {
    private static final Map<Integer, Integer> creditModifiers = new HashMap<>();

    static {
        creditModifiers.put(0, 0);
        creditModifiers.put(1, 100);
        creditModifiers.put(2, 300);
        creditModifiers.put(3, 1000);
    }

    /**
     * 
     * Returns the credit modifier for the given personal code.
     * 
     * @param personalCode the personal code of the borrower used to calculate the
     *                     credit modifier
     * 
     * @return the credit modifier based on the personal code
     */
    public int getCreditModifier(String personalCode) {
        int mod = (int) (Long.parseLong(personalCode) % 4);
        return creditModifiers.get(mod);
    }
}
