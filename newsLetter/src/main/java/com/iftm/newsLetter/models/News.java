package com.iftm.newsLetter.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "news")
public class News {

    @Id
    private ObjectId id;
    private String title;
    private String date;
    private String editorName;
    private List<Post> posts;

    
    public News() {
    }


    public News(ObjectId id, String title, String date, String editorName, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.editorName = editorName;
        this.posts = posts;
    }


    public ObjectId getId() {
        return id;
    }


    public void setId(ObjectId id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getEditorName() {
        return editorName;
    }


    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }


    public List<Post> getPosts() {
        return this.posts;
    }


    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((editorName == null) ? 0 : editorName.hashCode());
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        News other = (News) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (editorName == null) {
            if (other.editorName != null)
                return false;
        } else if (!editorName.equals(other.editorName))
            return false;
        if (posts == null) {
            if (other.posts != null)
                return false;
        } else if (!posts.equals(other.posts))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "News [id=" + id + ", title=" + title + ", date=" + date + ", editorName=" + editorName + ", posts="
                + posts + "]";
    }

    

    
}
