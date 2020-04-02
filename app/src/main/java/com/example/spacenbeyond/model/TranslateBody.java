package com.example.spacenbeyond.model;

public class TranslateBody {
    public String q;
    public String source;
    public String target;
    public String format;

    public TranslateBody(String q, String source, String target, String format) {
        this.q = q;
        this.source = source;
        this.target = target;
        this.format = format;
    }
}