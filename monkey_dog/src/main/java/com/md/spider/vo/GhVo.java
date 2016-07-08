package com.md.spider.vo;

public class GhVo {

    String name;
    String code;
    String img;
    String href;

    public GhVo(String name, String code, String img, String href) {
        super();
        this.name = name;
        this.code = code;
        this.img = img;
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
