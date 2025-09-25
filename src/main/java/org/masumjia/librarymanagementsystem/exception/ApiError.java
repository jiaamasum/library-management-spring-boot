package org.masumjia.librarymanagementsystem.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
    public LocalDateTime timestamp = LocalDateTime.now();
    public int status;
    public String error;
    public String message;
    public String path;
    public Map<String, String> fieldErrors;

    public ApiError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
