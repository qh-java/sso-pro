package com.huaita.ssoclient.exception;

public class BaseException  extends AbstractNestedRuntimeException  {

    private static final long serialVersionUID = 8458544317507845657L;
    private String code;
    private String friendlyMessage = "";
    private Object[] messageArgs;
    private String defaultFriendlyMessage;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String logMsg) {
        super(logMsg);
    }

    public BaseException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
    }

    public BaseException(String code, Throwable cause, Object[] messageArgs) {
        super(cause);
        this.code = code;
        this.messageArgs = messageArgs;
    }

    public BaseException(String code, String logMsg) {
        super(logMsg);
        this.code = code;
    }

    public BaseException(String code, String logMsg, Object[] messageArgs) {
        super(logMsg);
        this.code = code;
        this.messageArgs = messageArgs;
    }

    public BaseException(String code, String logMsg, Throwable cause) {
        super(logMsg, cause);
        this.code = code;
    }

    public BaseException(String code, String logMsg, Throwable cause, Object[] messageArgs) {
        super(logMsg, cause);
        this.code = code;
        this.messageArgs = messageArgs;
    }

    public BaseException(String code, Throwable cause, String defaultFriendlyMessage) {
        super(cause);
        this.code = code;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public BaseException(String code, Throwable cause, Object[] messageArgs, String defaultFriendlyMessage) {
        super(cause);
        this.code = code;
        this.messageArgs = messageArgs;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public BaseException(String code, String logMsg, String defaultFriendlyMessage) {
        super(logMsg);
        this.code = code;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public BaseException(String code, String logMsg, Object[] messageArgs, String defaultFriendlyMessage) {
        super(logMsg);
        this.code = code;
        this.messageArgs = messageArgs;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public BaseException(String code, String logMsg, Throwable cause, String defaultFriendlyMessage) {
        super(logMsg, cause);
        this.code = code;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public BaseException(String code, String logMsg, Throwable cause, Object[] messageArgs, String defaultFriendlyMessage) {
        super(logMsg, cause);
        this.code = code;
        this.messageArgs = messageArgs;
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }

    public String getMessage() {
        if (this.code != null && this.code.trim().length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Code: ").append(this.code).append("\rMessage: ").append(super.getMessage());
            return sb.toString();
        } else {
            return super.getMessage();
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getFriendlyMessage() {
        return this.friendlyMessage;
    }

    public void setFriendlyMessage(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
    }

    public Object[] getMessageArgs() {
        return this.messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

    public String getDefaultFriendlyMessage() {
        return this.defaultFriendlyMessage;
    }

    public void setDefaultFriendlyMessage(String defaultFriendlyMessage) {
        this.defaultFriendlyMessage = defaultFriendlyMessage;
    }
}
