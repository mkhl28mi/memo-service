package io.github.mkhl28mi.memo_service.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8082345078181155767L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
