package org.techtown.booktree_firebase;

public class PostInfo {
    private String postTitle;
    private String postContent;
    private String book_genre;
    private String book_style;
    private String userId;

    public PostInfo(String postTitle, String postContent, String book_genre, String book_style, String userId) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.book_genre = book_genre;
        this.book_style = book_style;
        this.userId = userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    

    public String getBook_genre() {
        return book_genre;
    }

    public void setBook_genre(String book_genre) {
        this.book_genre = book_genre;
    }

    public String getBook_style() {
        return book_style;
    }

    public void setBook_style(String book_style) { this.book_style = book_style; }
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}