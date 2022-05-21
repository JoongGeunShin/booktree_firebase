package org.techtown.booktree_firebase;

public class CommentDTO {
    private String userName;
    private String comment;

    public CommentDTO() {}

    public CommentDTO(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }


}
