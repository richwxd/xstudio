package io.github.xbeeant.mdc;

import java.util.UUID;

public class Constant {
    public static final String MDC_KEY = "TRACE_ID";

    private Constant() {
        throw new IllegalStateException("Utility class");
    }

    public static String traceId() {
        return UUID.randomUUID().toString();
    }
}
