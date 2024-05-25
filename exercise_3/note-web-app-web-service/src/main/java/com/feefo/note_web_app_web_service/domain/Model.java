package com.feefo.note_web_app_web_service.domain;

import static java.lang.String.format;
import static java.util.Objects.isNull;

abstract public class Model {

    protected <T> T validate(String field, T data) {
        if (isNull(data)) throw new ConstraintException(format("%s should not be a null value", field));
        return data;
    }
}
