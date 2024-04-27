package com.sami.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static Map<String, String> fieldError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField,
                            e -> e.getDefaultMessage() != null ? e.getDefaultMessage() : "")
                    );
        }
        return Collections.emptyMap();
    }

    public static Map<String, String> objectError(Errors errors) {
        if (errors.hasGlobalErrors()) {
            return errors.getGlobalErrors().stream()
                    .collect(Collectors.toMap(ge -> ge.getObjectName().toLowerCase(),
                            ge -> ge.getDefaultMessage() != null ? ge.getDefaultMessage() : "")
                    );
        }
        return Collections.emptyMap();
    }
}