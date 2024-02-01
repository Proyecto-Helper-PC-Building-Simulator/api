package es.bit.api.persistence.model.componenttables;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.bit.api.persistence.model.componenttables.enums.StorageTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "storages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Storage extends Component {
    private Integer size;
    private Integer transferSpeed;

    @Enumerated(EnumType.STRING)
    private StorageTypes type;


    public Storage() {
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

    public StorageTypes getType() {
        return type;
    }

    public void setType(StorageTypes storageTypes) {
        this.type = storageTypes;
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
