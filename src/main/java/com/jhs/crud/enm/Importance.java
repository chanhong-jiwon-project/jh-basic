package com.jhs.crud.enm;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Importance {
    high("상"),
    middle("중"),
    low("하");

    final private String name;
    private Importance(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static Importance nameOf(String name) {
        for (Importance importance : Importance.values()) {
            if (importance.getName().equals(name)) {
                return importance;
            }
        }
        return null;
    }
}
