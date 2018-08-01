package org.abalong.controller.services;

import org.abalong.controller.daos.FieldsDao;
import org.abalong.model.entities.Field;
import org.abalong.model.entities.Option;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Instances of this class are used to operate with {@code Field} instances in the database throw {@code FieldDao}.
 */
public class FieldsService {

    private FieldsDao dao = new FieldsDao();

    /**
     * This method checls whether the {@param field} is correct.<br>
     * The {@code field} has not to be null, label has not to be empty
     * @param field
     * @return true if the {@code field} is correct
     */
    private boolean isCorrect(Field field) {
        if (field == null) {
            return false;
        }

        if (StringUtils.isEmpty(field.getLabel())) {
            return false;
        }

        return field.getFieldType() != null;
    }

    /**
     * Returns true if there is such field in the database
     * @param field
     * @return
     */
    private boolean exists(Field field) {
        if (field == null)
            return false;

        return dao.getField(field.getLabel()) != null;
    }

    public boolean save(Field field) {
        if (!isCorrect(field)) {
            return false;
        }

        if (exists(field)) {
            return false;
        }

        dao.save(field);
        return true;
    }

    public boolean update(Field field) {
        if (!isCorrect(field)) {
            return false;
        }

        if (!exists(field)) {
            return false;
        }

        dao.update(field);
        return true;
    }

    public boolean delete(Field field) {
        if (!isCorrect(field)) {
            return false;
        }

        if (!exists(field))
            return false;

        dao.delete(field);
        return true;
    }

    public Field getField(String label) {
        return dao.getField(label);
    }

    public List<Field> getAllFields() {
        return dao.getAllFields();
    }

    /**
     *
     * @param label Label of the {@code Field} instance.
     * @return all {@code Option}s which {@code Field} instance contains.
     */
    public List<Option> getOptions(String label) {
        return dao.getOptions(label);
    }
}
