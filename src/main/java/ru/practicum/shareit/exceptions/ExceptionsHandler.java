package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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

//        @ExceptionHandler
//        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//        public ErrorResponse handleThrowable(final Throwable exp) {
//            log.error("Произошла непредвиденная ошибка.{}", exp.getMessage(), exp);
//            return new ErrorResponse("Произошла непредвиденная ошибка.");
//        }

    }