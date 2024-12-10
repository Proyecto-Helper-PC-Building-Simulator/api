package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;

@Entity
@Table(name = "component_types")
public class ComponentType extends GenericAttribute {
    private String apiName;


    public ComponentType() {}


    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
