package soa.collectionservice.collectionservice.exception;

import lombok.*;
import soa.collectionservice.collectionservice.model.dto.ErrorDto;
import soa.collectionservice.collectionservice.model.dto.InvalidParamDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvalidInputException extends RuntimeException {
    private List<InvalidParamDto> invalidParams;
    private ErrorDto error;
}
