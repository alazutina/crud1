package com.anna.crud.util;

public enum SQLQuery {

    TAG_GET_BY_ID_QUERY("SELECT * FROM tags WHERE id = %;"),
    TAG_GET_ALL_QUERY("SELECT * FROM tags;"),
    TAG_POST_DELETE_BY_ID_TAG_QUERY("DELETE * FROM tag_post WHERE id_tag = %;"),
    TAG_DELETE_BY_ID_QUERY("DELETE * FROM tags WHERE id = %;"),
    TAG_INSERT_QUERY("INSERT INTO tag (ID, NAME) VALUES (0, %)"),
    POST_DELETE_BY_ID_QUERY("DELETE * FROM posts WHERE id = %"),
    TAG_POST_DELETE_BY_ID_POST_QUERY("DELETE * FROM tag_post WHERE id_post = %"),
    POST_GET_BY_ID_QUERY("SELECT * FROM posts WHERE id = %"),
    WRITER_DELETE_BY_ID_QUERY("DELETE * FROM writers WHERE id = %"),
    WRITER_GET_BY_ID_QUERY("SELECT * FROM writers WHERE id = %"),
    WRITER_INSERT_QUERY("INSERT INTO writers (ID, NAME) VALUES (0, %)");


    private String value;

    SQLQuery(String s) {
        this.value=s;
    }

    public String getValue() {
        return this.value;
    }

}
