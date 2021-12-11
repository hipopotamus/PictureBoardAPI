package pictureboard.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pictureboard.api.dto.ErrorResult;
import pictureboard.api.dto.FieldErrorDto;
import pictureboard.api.dto.FormErrorResult;
import pictureboard.api.exception.IllegalFormException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice(annotations = RestController.class)
@Component
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class})
    public ErrorResult basicExHandler(RuntimeException e) {
        return new ErrorResult("400", e.getClass().getSimpleName(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalFormException.class)
    public FormErrorResult IllegalFormExHandler(IllegalFormException e) {
        List<FieldErrorDto> fieldErrorDtoList = getFieldErrorDtoList(e.getErrors().getAllErrors());

        return new FormErrorResult("400", e.getClass().getSimpleName(), fieldErrorDtoList);
    }

    private List<FieldErrorDto> getFieldErrorDtoList(List<ObjectError> errors) {
        List<FieldErrorDto> fieldErrorDtoList = new ArrayList<>();
        for (ObjectError error : errors) {
            int pos = error.getCodes()[0].lastIndexOf("." + 1);
            String field = error.getCodes()[0].substring(pos);
            String message = getErrorMessage(error);
            fieldErrorDtoList.add(new FieldErrorDto(field, message));
        }
        return fieldErrorDtoList;
    }

    private String getErrorMessage(ObjectError error) {
        String[] codes = error.getCodes();

        for (String code : codes) {
            try {
                String message = messageSource.getMessage(code, null, Locale.KOREA);
                return message;
            } catch (Exception e) {
                continue;
            }
        }
        return error.getDefaultMessage();
    }


}
