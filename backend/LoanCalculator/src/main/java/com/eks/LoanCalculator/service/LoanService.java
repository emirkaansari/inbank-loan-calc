package com.eks.LoanCalculator.service;

import org.springframework.stereotype.Service;

import com.eks.LoanCalculator.pojo.LoanDecision;

@Service
public class LoanService {

    private final CreditModifierRegistry creditModifierRegistry;
    
    /**
     * Constructor for LoanService.
     * 
     * @param creditModifierRegistry - An instance of CreditModifierRegistry.
     */

    public LoanService(CreditModifierRegistry creditModifierRegistry) {
        this.creditModifierRegistry = creditModifierRegistry;
    }
    /**
     * Returns a LoanDecision object based on the provided personalCode, loanAmount and loanPeriod.
     * 
     * @param personalCode - A string representing the personal code of the borrower.
     * @param loanAmount - An integer representing the requested loan amount.
     * @param loanPeriod - An integer representing the requested loan period in months.
     * @return - A LoanDecision object containing information about the loan decision.
     */

    public LoanDecision getLoanDecision(String personalCode, int loanAmount, int loanPeriod) {
        int creditModifier = creditModifierRegistry.getCreditModifier(personalCode);
        double creditScore = (double) creditModifier / loanAmount * loanPeriod;

        if (creditScore >= 1) {
            int maxLoanAmount = getMaxLoanAmount(loanPeriod, creditModifier);
            return new LoanDecision("positive", maxLoanAmount);
        } else {
            for (int i = loanPeriod + 12; i <= 60; i += 12) {
                double newCreditScore = (double) creditModifier / loanAmount * i;
                if (newCreditScore >= 1) {
                    int maxLoanAmount = getMaxLoanAmount(i, creditModifier);
                    return new LoanDecision("positive", maxLoanAmount, i);
                }
            }
            return new LoanDecision("negative", 0);
        }
    }
     /**
     * Calculates and returns the maximum loan amount based on the provided loan period and credit modifier.
     * 
     * @param loanPeriod - An integer representing the requested loan period in months.
     * @param creditModifier - An integer representing the credit modifier of the borrower.
     * @return - An integer representing the maximum loan amount.
     */
    
    private int getMaxLoanAmount(int loanPeriod, int creditModifier) {
        int maxLoanAmount = creditModifier / loanPeriod * 100;
        return Math.min(maxLoanAmount, 10000);
    }
}
