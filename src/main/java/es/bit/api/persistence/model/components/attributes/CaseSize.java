package es.bit.api.persistence.model.components.attributes;

import jakarta.persistence.*;

@Entity
@Table(name = "case_sizes")
public class CaseSize extends GenericAttribute {
    public CaseSize() {}
}
