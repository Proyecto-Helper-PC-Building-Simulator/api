package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.Component;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RamMemoryDTO extends ComponentDTO {
    private Integer size;
    private Integer frequency;


    public RamMemoryDTO() {
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
