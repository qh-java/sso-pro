package com.huaita.ssoclient.exception;

import com.huaita.ssoclient.util.CommonExceptionUtils;

public abstract class AbstractNestedRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 7534751658054481518L;

    public AbstractNestedRuntimeException(Throwable cause) {
        super(cause);
    }

    public AbstractNestedRuntimeException(String msg) {
        super(msg);
    }

    public AbstractNestedRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getMessage() {
        return CommonExceptionUtils.buildMessage(super.getMessage(), this.getCause());
    }

    public Throwable getRootCause() {
        Throwable rootCause = null;

        for(Throwable cause = this.getCause(); cause != null && cause != rootCause; cause = cause.getCause()) {
            rootCause = cause;
        }

        return rootCause;
    }

    public Throwable getMostSpecificCause() {
        Throwable rootCause = this.getRootCause();
        return (Throwable)(rootCause != null ? rootCause : this);
    }

    public boolean contains(Class exType) {
        if (exType == null) {
            return false;
        } else if (exType.isInstance(this)) {
            return true;
        } else {
            Throwable cause = this.getCause();
            if (cause == this) {
                return false;
            } else if (cause instanceof AbstractNestedRuntimeException) {
                return ((AbstractNestedRuntimeException)cause).contains(exType);
            } else {
                while(cause != null) {
                    if (exType.isInstance(cause)) {
                        return true;
                    }

                    if (cause.getCause() == cause) {
                        break;
                    }

                    cause = cause.getCause();
                }

                return false;
            }
        }
    }
}
