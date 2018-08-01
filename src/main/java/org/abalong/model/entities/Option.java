package org.abalong.model.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This class represents an option in the {@code Field} entity.<br>
 * It contains own unique {@code id, optionText} and {@code field} that contains this option.
 * A field entity may contain several options,
 * so, table {@code options} in the database has Many-To-One relation.
 */
@Entity
@Table(name = "options")
public class Option {

    private long    id;

    private String  optionText;

    private Field   field;

    public Option() {

    }

    public Option(Field field, String text) {
        this.field = field;
        this.optionText = text;
    }

    /**
     * Value of {@code id} field is generated by the database.
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    public long getId() {
        return id;
    }

    @Column(name = "option", nullable = false)
    public String getOptionText() {
        return optionText;
    }

    /**
     * Many-To-One relation to a field entity in the database.
     * @return
     */
    @ManyToOne(targetEntity = Field.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "field_label", foreignKey = @ForeignKey(name = "field_label_fk"))
    public Field getField() {
        return field;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return String.format(
                "Field: %s%nOption: %s%n",
                field.getLabel(), optionText
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optionText, field);
    }
}