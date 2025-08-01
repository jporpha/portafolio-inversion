package com.jp.orpha.portafolio_inversion.exceptions;

public class NotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public NotFoundException(Object caller, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'",
                caller.getClass().getSimpleName().replace("ServiceImpl", "").replace("Service", ""),
                fieldName,
                fieldValue));
        this.resourceName = caller.getClass().getSimpleName();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
