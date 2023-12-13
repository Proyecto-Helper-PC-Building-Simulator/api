package es.bit.api.rest.dto.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.StorageTypes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageDTO extends ComponentDTO {
    private Integer size;
    private Integer transferSpeed;
    private StorageTypes storageTypes;


    public StorageDTO() {
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTransferSpeed() {
        return transferSpeed;
    }

    public void setTransferSpeed(Integer transferSpeed) {
        this.transferSpeed = transferSpeed;
    }

    public StorageTypes getStorageTypes() {
        return storageTypes;
    }

    public void setStorageTypes(StorageTypes storageTypes) {
        this.storageTypes = storageTypes;
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
