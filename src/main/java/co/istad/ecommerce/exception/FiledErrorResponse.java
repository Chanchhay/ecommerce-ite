package co.istad.ecommerce.exception;

public record FiledErrorResponse(
        String filed,
        String reason
) {
}