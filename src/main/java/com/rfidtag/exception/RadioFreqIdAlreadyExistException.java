package com.rfidtag.exception;

import java.io.Serial;

/**
 * Radio frequency identification Already Exist Exception
 *
 * @author Reem Gharib
 */
public class RadioFreqIdAlreadyExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1L;

    public RadioFreqIdAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RadioFreqIdAlreadyExistException(String message) {
        super(message);
    }

    public RadioFreqIdAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
