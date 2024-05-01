package com.rfidtag.exception;

import java.io.Serial;

/**
 * Radio frequency identification persist exception
 *
 * @author Reem Gharib
 */
public class RadioFreqIdPersistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * RFID tag persist Exception
     *
     * @param message the message
     * @param cause   the throwable
     */
    public RadioFreqIdPersistException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * RFID tag persist Exception
     *
     * @param message the message
     */
    public RadioFreqIdPersistException(String message) {
        super(message);
    }

}
