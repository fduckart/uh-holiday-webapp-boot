package edu.hawaii.its.holiday.type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "type")
public class Type implements Serializable {

    public static final long serialVersionUID = 43L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "version")
    private Integer version;

    @Column(name = "description")
    private String description;

    @Column(name = "sort_id")
    @JsonIgnore
    private Integer sortId;

    public Type() {
        // Empty.
    }

    public Type(Integer id, String description, Integer version) {
        this.id = id;
        this.description = description;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortId() {
        return sortId;
    }

    @Override
    public String toString() {
        return "Type ["
                + "id=" + id
                + ", description=" + description
                + "]";
    }

}
