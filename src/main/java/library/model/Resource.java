package library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "resources")
public class Resource {


    @Id
    private String id;
    private String kindOfResource;
    private String title;
    private String numberOfPages;
    private String lastResourceLoanDate;
    private int quantityAvailable;
    private String subject;
    private String author;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindOfResource() {
        return kindOfResource;
    }

    public void setKindOfResource(String kindOfResource) {
        this.kindOfResource = kindOfResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getLastResourceLoanDate() {
        return lastResourceLoanDate;
    }

    public void setLastResourceLoanDate(String lastResourceLoanDate) {
        this.lastResourceLoanDate = lastResourceLoanDate;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
