package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;

@Entity
@Table(name = "lightings")
public class Lighting extends GenericAttribute {
    public Lighting() {}
}
