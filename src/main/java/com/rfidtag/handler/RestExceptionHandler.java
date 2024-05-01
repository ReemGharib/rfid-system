package com.rfidtag.handler;

import com.rfidtag.dtos.ErrorResponse;
import com.rfidtag.exception.RadioFreqIdAlreadyExistException;
import com.rfidtag.exception.RadioFreqIdDeleteException;
import com.rfidtag.exception.RadioFreqIdNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Reem Gharib
 */
@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorResponse> handleExceptions(Throwable e) {

        log.error("ERROR", e);
        return new ResponseEntity<>(this.getErrorResponse("ERROR_", "An error occurred, contact the admin to check server logs", e)
                , null,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(BadRequestException e) {

        log.error("ERROR", e);
        return new ResponseEntity<>(this.getErrorResponse("ERROR_", e.getMessage(), e)
                , null,
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(RadioFreqIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(RadioFreqIdNotFoundException e) {
        log.error("ERROR", e);
        return new ResponseEntity<>(this.getErrorResponse("ERR_", e.getMessage(), e), null, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(RadioFreqIdAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(RadioFreqIdAlreadyExistException e) {

        log.error("ERROR", e);
        return new ResponseEntity<>(this.getErrorResponse("ERR_", e.getMessage(), e), null, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(RadioFreqIdDeleteException.class)
    public ResponseEntity<ErrorResponse> handleRadioFreqIdDeleteException(RadioFreqIdAlreadyExistException e) {

        log.error("ERROR", e);
        return new ResponseEntity<>(this.getErrorResponse("ERR_", e.getMessage(), e), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get Error response
     *
     * @param code    the code
     * @param message the message
     * @param e       the throwable e
     * @return the ErrorResponse
     */
    protected ErrorResponse getErrorResponse(String code, String message, Throwable e) {

        return ErrorResponse.builder()
                .code(code)
                .description(StringUtils.isBlank(message) ? e.getMessage() : message)
                .reason(e.getClass().getSimpleName())
                .build();
    }
}
