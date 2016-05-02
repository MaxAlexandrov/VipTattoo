package ru.aleksandrov.news;

public class Post {
    public  String id_news;
    public  String text;
    public  String data_date;
    public  String src_small;
    public  String src;
    public  String src_big;
    public  String comments;
    public  String likes;
    public  String repost;

    public String getId_news() {
        return id_news;
    }

    public void setId_news(String id_news) {
        this.id_news = id_news;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getData_date() {
        return data_date;
    }

    public void setData_date(String data_date) {
        this.data_date = data_date;
    }

    public String getSrc_small() {
        return src_small;
    }

    public void setSrc_small(String src_small) {
        this.src_small = src_small;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc_big() {
        return src_big;
    }

    public void setSrc_big(String src_big) {
        this.src_big = src_big;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRepost() {
        return repost;
    }

    public void setRepost(String repost) {
        this.repost = repost;
    }
}
