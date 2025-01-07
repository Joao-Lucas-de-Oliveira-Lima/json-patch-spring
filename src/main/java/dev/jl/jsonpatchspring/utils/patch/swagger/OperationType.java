package dev.jl.jsonpatchspring.utils.patch.swagger;

import lombok.Getter;

@Getter
public enum OperationType {
    ADD("add"),
    REPLACE("replace"),
    REMOVE("remove"),
    TEST("test"),
    COPY("copy"),
    MOVE("move");

    private final String operationName;

    OperationType(String operationName) {
        this.operationName = operationName;
    }
}
