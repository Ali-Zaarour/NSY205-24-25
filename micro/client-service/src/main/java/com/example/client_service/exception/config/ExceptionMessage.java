package com.example.client_service.exception.config;

import lombok.experimental.UtilityClass;

/**
 * Utility class that contains constants for exception messages used throughout the application.
 * <p>
 * These constants can be used to standardize exception messages, ensuring consistency and
 * facilitating easier maintenance and localization if needed.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * if (user == null) {
 *     throw new CustomException(ExceptionMessageConstant.USERNAME_NOT_FOUND);
 * }
 * }</pre>
 * </p>
 * <p>
 * Note: This class is not meant to be instantiated.
 * </p>
 */
@UtilityClass
public class ExceptionMessage {
    public final String USERNAME_NOT_FOUND = "username.not.found";
    public final String USER_NOT_FOUND = "user.not.found";
    public final String DATA_INTEGRITY_VIOLATION = "data.conflict.detected";
    public final String UNMATCHED_PATTERN = "unmatched.pattern";
    public final String UNAUTHORIZED_ACTION = "unauthorized.action";
    public final String NOTHING_TO_UPDATE = "nothing.to.update";
}
