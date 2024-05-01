package com.rfidtag.exception;

import java.io.Serial;

/**
 * Radio frequency identification delete exception
 *
 * @author Reem Gharib
 */
public class RadioFreqIdDeleteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * RFID tag persist Exception
     *
     * @param message the message
     * @param cause   the throwable
     */
    public RadioFreqIdDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RFID tag persist Exception
     *
     * @param message the message
     */
    public RadioFreqIdDeleteException(String message) {
        super(message);
    }

}
