package com.eks.LoanCalculator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.eks.LoanCalculator.pojo.LoanDecision;
import com.eks.LoanCalculator.service.LoanService;
/**
 * Controller for handling loan related requests.
 */
@Controller
@RequestMapping("/loan")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {

    private final LoanService loanService;
    /**
     * Constructor for initializing the LoanController with a LoanService instance.
     *
     * @param loanService the LoanService instance used to handle loan related operations.
     */
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    /**
     * Endpoint for getting a loan decision based on the provided loan parameters.
     *
     * @param personalCode the personal code of the loan applicant.
     * @param loanAmount the amount requested for the loan.
     * @param loanPeriod the period of the loan in months.
     * @return a ResponseEntity containing the loan decision.
     */
    @GetMapping
    public ResponseEntity<LoanDecision> getLoanDecision(
            @RequestParam("personalCode") String personalCode,
            @RequestParam("loanAmount") int loanAmount,
            @RequestParam("loanPeriod") int loanPeriod) {
        LoanDecision decision = loanService.getLoanDecision(personalCode, loanAmount, loanPeriod);
        return ResponseEntity.ok(decision);
    }

}
