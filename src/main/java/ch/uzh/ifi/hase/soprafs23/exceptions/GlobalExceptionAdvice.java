package ch.uzh.ifi.hase.soprafs23.exceptions;

import ch.uzh.ifi.hase.soprafs23.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    protected Result handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        ex.printStackTrace();
        return Result.success(ex.getMessage());
    }

    @ExceptionHandler(TransactionSystemException.class)
    public Result handleTransactionSystemException(Exception ex, HttpServletRequest request) {
        String format = String.format("Request: %s raised %s", request.getRequestURL(), ex.getMessage());
        log.error(format);
        return Result.error(format);
    }

    // Keep this one disable for all testing purposes -> it shows more detail with
    // this one disabled
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public Result handleException(Exception ex,HttpServletRequest request) {
        String format = String.format("Request: %s raised %s", request.getRequestURL(), ex.getMessage());
        log.error(format );
        return Result.error(format);
    }
}