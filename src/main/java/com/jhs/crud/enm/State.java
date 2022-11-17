package com.jhs.crud.enm;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum State {
    open("열림"),
    progress("진행"),
    check("확인"),
    reopen("재개"),
    close("닫힘");

    final private String name;

    private State(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static State nameOf(String name) {
        for (State state : State.values()) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }
}
