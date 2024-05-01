package com.rfidtag.exception;

/**
 * Radio freq identification not found exception
 *
 * @author Reem Gharib
 */
public class RadioFreqIdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    /**
     * RFID Not Found Exception
     *
     * @param message the message
     * @param cause   the throwable
     */
    public RadioFreqIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RFID Not Found Exception
     *
     * @param message the message
     */
    public RadioFreqIdNotFoundException(String message) {
        super(message);
    }

    /**
     * RFID Not Found Exception
     *
     * @param cause the cause
     */
    public RadioFreqIdNotFoundException(Throwable cause) {
        super(cause);
    }
}

