package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



    @Slf4j
    @RestControllerAdvice
    public class ExceptionsHandler {

        @ExceptionHandler(IncorrectParameterException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ErrorResponse handleValidateUser(final RuntimeException exp) {
            return new ErrorResponse(exp.getMessage());
        }

        @ExceptionHandler({NotFoundException.class}              )
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ErrorResponse handleFilmNotFound(final RuntimeException exp) {
            return new ErrorResponse(exp.getMessage());
        }

        @ExceptionHandler
        @ResponseStatus(HttpStatus.CONFLICT)
        public ErrorResponse duplicatedExceptionHandler(DuplicatedEmailException exp) {
            return new ErrorResponse(exp.getMessage());
        }


    }