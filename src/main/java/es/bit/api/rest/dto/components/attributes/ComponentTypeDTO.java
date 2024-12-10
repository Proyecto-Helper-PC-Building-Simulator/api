package es.bit.api.rest.dto.components.attributes;

public class ComponentTypeDTO extends GenericAttributeDTO {
    private String apiName;


    public ComponentTypeDTO() {}


    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
