package org.abalong.controller.cdi;

import org.abalong.controller.services.FieldsService;
import org.abalong.model.entities.Field;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * This CDI bean is used to get all {@code Field}s from the database
 * and change choosed {@code Field} in the table on {@code fields.xhtml} page.
 */
@Named
@SessionScoped
public class FieldsBean implements Serializable {

    private Field selectedField;

    private FieldsService service = new FieldsService();

    public Field getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(Field selectedField) {
        this.selectedField = selectedField;
    }

    public List<Field> getFields() {
        return service.getAllFields();
    }

    public void delete() {
        service.delete(selectedField);
    }
}
