package com.influencer.platform;

/**
 * Base exception for the influencer platform.
 * Use this for generic platform errors.
 */
public class PlatformException extends Exception {
    public PlatformException(String message) {
        super(message);
    }
}

/**
 * Thrown when a contract lookup fails.
 */
public class ContractNotFoundException extends PlatformException {
    public ContractNotFoundException(String contractId) {
        super("Contract not found: " + contractId);
    }
}

/**
 * Thrown when payment processing fails.
 */
public class InvalidPaymentException extends PlatformException {
    public InvalidPaymentException(String errorDetails) {
        super("Payment failed: " + errorDetails);
    }
}