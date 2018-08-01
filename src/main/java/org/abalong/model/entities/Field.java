package org.abalong.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Field} class represents a field entity in the database
 * Entities of this class can be created on /field page.
 * {@code label} field of the class is unique. It is necessary with {@code fieldType} field.
 * Instances of this class will be saved in {@code fields} table in the database.
 */
@Entity
@Table(name = "fields")
public class Field {

    private String      label;
    private FieldType   fieldType;
    private boolean     isRequired;
    private boolean     isActive;

    private List<Option> options = new ArrayList<Option>();

    public Field() {

    }

    public Field(String label, FieldType fieldType) {
        this.label = label;
        this.fieldType = fieldType;
    }

    /**
     * This is ID of the field instance. Can not be repeated.
     * @return value of the {@code label}'s field.
     */
    @Id
    @Column(name = "label", unique = true, nullable = false)
    public String getLabel() {
        return label;
    }

    /**
     * A type of the {@code Field} instance.
     * The value of this field will be saved in String form of the type in the database.
     * @return
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    public FieldType getFieldType() {
        return fieldType;
    }

    @Column(name = "required")
    public boolean isRequired() {
        return isRequired;
    }

    @Column(name = "active")
    public boolean isActive() {
        return isActive;
    }

    /**
     * List of {@code Option}'s entities. The options are used in @{COMBO_BOX, RADIO_BUTTON, CHECK_BOX} cases.
     * @return options field.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Option.class, orphanRemoval = true, mappedBy = "field")
    public List<Option> getOptions() {
        return options;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String stringType() {
        return FieldType.stringType(fieldType);
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
    }

    @Override
    public String toString() {
        return String.format(
                "Label: %s%nType: %s%n",
                label, fieldType.toString()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(label, field.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
