package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "manufacturers")
public class Manufacturer extends GenericAttribute {
    public Manufacturer() {}
}
