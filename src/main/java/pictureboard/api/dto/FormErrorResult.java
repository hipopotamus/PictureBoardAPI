package pictureboard.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FormErrorResult {
    private String status;

    private String exception;

    private List<FieldErrorDto> fieldErrorList;
}
