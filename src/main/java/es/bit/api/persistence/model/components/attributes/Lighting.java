package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "lightings")
public class Lighting extends GenericAttribute {
}
