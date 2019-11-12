package com.huaita.ssoclient.util;

import com.huaita.ssoclient.model.ConfigConstant;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonExceptionUtils {
    public CommonExceptionUtils() {
    }

    public static String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message).append("; ");
            }

            sb.append("nested exception is ").append(cause);
            return sb.toString();
        } else {
            return message;
        }
    }

    public static Throwable getRootCause(Throwable ex) {
        Throwable rootCause = null;

        for(Throwable cause = ex.getCause(); cause != null && cause != rootCause; cause = cause.getCause()) {
            rootCause = cause;
        }

        return rootCause;
    }

    public static Throwable getMostSpecificCause(Throwable ex) {
        Throwable rootCause = getRootCause(ex);
        return rootCause != null ? rootCause : ex;
    }

    public static String getExceptionStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    public static String getMostSpecificCauseMessageInfo(Throwable ex) {
        Throwable rootCause = getMostSpecificCause(ex);
        StackTraceElement[] elements = rootCause.getStackTrace();
        String rootException = rootCause.getClass().getName();
        String rootExceptionMsg = rootCause.getLocalizedMessage();
        String rootCauseSpotClass = elements[0].getClassName();
        String rootCauseSpotMethod = elements[0].getMethodName();
        int rootCauseSpotLine = elements[0].getLineNumber();
        StringBuilder sbdr = new StringBuilder(ConfigConstant.LINE_SEPARATOR);
        sbdr.append("[Root Exception]: ").append(rootException).append(ConfigConstant.LINE_SEPARATOR).append("[Root Exception Message]: ").append(rootExceptionMsg).append(ConfigConstant.LINE_SEPARATOR).append("[Root Exception throwed on]: ").append(rootCauseSpotClass).append(".").append(rootCauseSpotMethod).append("  Line:").append(rootCauseSpotLine);
        return sbdr.toString();
    }

}
