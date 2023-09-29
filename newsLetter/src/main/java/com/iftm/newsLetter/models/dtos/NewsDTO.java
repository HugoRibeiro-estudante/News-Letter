package com.iftm.newsLetter.models.dtos;

import java.io.Serializable;
import java.util.List;

import com.iftm.newsLetter.models.News;
import org.bson.types.ObjectId;

import com.iftm.newsLetter.models.Post;


public class NewsDTO implements Serializable {


    private ObjectId id;
    private String title;
    private String date;
    private String editorName;
    private List<Post> posts;

    
    public NewsDTO() {
    }


    public NewsDTO(ObjectId id, String title, String date, String editorName, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.editorName = editorName;
        this.posts = posts;
    }

    public NewsDTO(News news) {
        if(news.getId() != null)
            this.id = news.getId();
        this.title = news.getTitle();
        this.date = news.getDate();
        this.editorName = news.getEditorName();
        this.posts = news.getPosts();
    }

    public News toNews() {
        ObjectId id = null;
        if(this.id != null)
            id = new ObjectId(String.valueOf(this.id));

        return new News(id,
                this.title,
                this.date,
                this.editorName,
                this.posts);
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
        return posts;
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
        NewsDTO other = (NewsDTO) obj;
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
