package dev.jl.jsonpatchspring.utils.patch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.flipkart.zjsonpatch.JsonPatchApplicationException;
import dev.jl.jsonpatchspring.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Set;

@Component
public class PatchValidator {
    private final ObjectMapper objectMapper;

    public PatchValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T validate(T object, Class<T> objectType, JsonNode patch)
            throws JsonProcessingException, BadRequestException {
        JsonNode objectAsJsonNode = objectMapper.convertValue(object, JsonNode.class);
        JsonNode patchedJsonNode;
        T patchedObject;

        try {
            patchedJsonNode = JsonPatch.apply(patch, objectAsJsonNode);
            patchedObject = objectMapper.treeToValue(patchedJsonNode, objectType);
        } catch (IllegalArgumentException | JsonPatchApplicationException e) {
            throw new BadRequestException(e.getMessage());
        }


        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(patchedObject);

        if (constraintViolations.isEmpty()) {
            return patchedObject;
        }

        BindingResult bindingResult = new BeanPropertyBindingResult(patchedObject, "patchedObject");

        constraintViolations.forEach(constraintViolation -> {
            String fieldName = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();

            FieldError fieldError
                    = new FieldError("constraintViolation", fieldName, message);
            bindingResult.addError(fieldError);
        });

        throw new BadRequestException("The provided data is missing or not in a valid format.", bindingResult);
    }

}
