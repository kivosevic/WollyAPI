package rs.vegait.wolly.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError {
    
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String message) {
        this.message = message;
    }

}
