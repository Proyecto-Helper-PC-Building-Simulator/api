package es.bit.api.persistence.model.components.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cable_types")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CableType extends GenericAttribute {
}
