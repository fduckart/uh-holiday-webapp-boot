package edu.hawaii.its.holiday.controller;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.hawaii.its.holiday.type.Type;

public class JsonData3 implements Serializable {

    private static final long serialVersionUID = 53L;

    private String key;
    private List<Type> data;

    public JsonData3(String key, List<Type> data) {
        this.key = key;
        this.data = data;
    }

    @JsonCreator
    public JsonData3(@JsonProperty("data") List<Type> data) {
        this("data", data);
    }

    @JsonIgnore
    public String getKey() {
        return key;
    }

    public List<Type> getData() {
        return data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonData3 other = (JsonData3) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "JsonData3 [key=" + key + ", data=" + data + "]";
    }

}
