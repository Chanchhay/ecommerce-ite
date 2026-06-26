package co.istad.ecommerce.exception;

public record FiledErrorResponse(
        String field,
        String reason
) {
}