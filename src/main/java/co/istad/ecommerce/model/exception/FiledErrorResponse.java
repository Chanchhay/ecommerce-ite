package co.istad.ecommerce.model.exception;

public record FiledErrorResponse(
        String filed,
        String reason
) {
}