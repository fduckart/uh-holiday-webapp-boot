package edu.hawaii.its.holiday.type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonRootName(value = "data")
public class HolidayDto implements Serializable {

    private static final long serialVersionUID = 53L;

    private Integer id;
    private String description;
    private LocalDate observedDate;
    private LocalDate officialDate;
    private Integer officialYear;
    private List<Type> types = new ArrayList<>();

    // Constructor.
    public HolidayDto() {
        // Empty.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getObservedDate() {
        return observedDate;
    }

    public void setObservedDate(LocalDate observedDate) {
        this.observedDate = observedDate;
    }

    public LocalDate getOfficialDate() {
        return officialDate;
    }

    public void setOfficialDate(LocalDate officialDate) {
        this.officialDate = officialDate;
    }

    @JsonSerialize(using = TypeSerializer.class)
    public List<Type> getTypes() {
        return types;
    }

    @JsonDeserialize(using = TypeDeserializer.class)
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Integer getOfficialYear() {
        return officialYear;
    }

    public void setOfficialYear(Integer officialYear) {
        this.officialYear = officialYear;
    }

    @Override
    public String toString() {
        return "HolidayDto [" +
                "officialYear=" + officialYear +
                ", description='" + description + '\'' +
                ", observedDate=" + observedDate +
                ", officialDate=" + officialDate +
                ", types=" + types +
                "]";
    }
}
