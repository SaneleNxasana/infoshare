package infoshare.domain.content;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user9 on 2016/02/12.
 */
public class PublishedContent implements Serializable, Comparable <PublishedContent> {
    private String id;
    private Date dateCreated;
    private String creator;
    private String source     ;
    private String category   ;
    private String title      ;
    private String content    ;
    private String contentType;
    private String status     ;
    private String state      ;
    private String org;

    public PublishedContent(Builder builder) {
        this.id = builder.id;
        this.dateCreated = builder.dateCreated;
        this.org = builder.org;
        this.creator = builder.creator;
        this.source = builder.source;
        this.category = builder.category;
        this.title = builder.title;
        this.content = builder.content;
        this.contentType = builder.contentType;
        this.state = builder.state;
        this.status = builder.status;
    }

    public String getOrg() {
        return org;
    }
    public String getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getCreator() {
        return creator;
    }

    public String getSource() {
        return source;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public String getStatus() {
        return status;
    }

    public String getState() {
        return state;
    }

    public static class Builder{
        private String id;
        private Date dateCreated;
        private String creator;
        private String source     ;
        private String category   ;
        private String title      ;
        private String content    ;
        private String contentType;
        private String status     ;
        private String state      ;
        public String org;

        public Builder title(String title){
            this.title = title;
            return this;
        }
        public Builder id(String id){
            this.id =id;
            return this;
        }

        public Builder org(String org) {
            this.org = org;
            return this;
        }
        public Builder dateCreated(Date dateCreated){
            this.dateCreated = dateCreated;
            return this;
        }
        public Builder creator(String creator){
            this.creator = creator;
            return  this;
        }
        public Builder source(String source){
            this.source = source;
            return this;
        }
        public Builder category(String category){
            this.category = category;
            return this;
        }
        public Builder content(String content){
            this.content = content;
            return this;
        }
        public Builder contentType(String contentType){
            this.contentType = contentType;
            return this;
        }
        public Builder status(String status){
            this.status = status;
            return this;
        }
        public Builder state(String state){
            this.state = state;
            return this;
        }

        public Builder copy(PublishedContent publishedContent){
            this.id = publishedContent.id;
            this.dateCreated = publishedContent.dateCreated;
            this.org = publishedContent.org;
            this.creator = publishedContent.creator;
            this.source = publishedContent.source;
            this.category = publishedContent.category;
            this.title = publishedContent.title;
            this.content = publishedContent.content;
            this.contentType = publishedContent.contentType;
            this.state = publishedContent.state;
            this.status = publishedContent.status;

            return this;
        }

        public PublishedContent build(){return new PublishedContent(this);}
    }
    public static Builder builder(){return new Builder();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublishedContent that = (PublishedContent) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(PublishedContent publishedContent) {
        return title.compareTo(publishedContent.title);
    }
}
