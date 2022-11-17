package com.jhs.crud.enm;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Item {
    fixBug("버그수정"),
    newDev("신규개발"),
    reqDB("DB요청"),
    reqAcs("접근권한요청"),
    codeReview("코드리뷰");

    final private String name;
    private Item(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public static Item nameOf(String name) {
        for (Item item : Item.values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
