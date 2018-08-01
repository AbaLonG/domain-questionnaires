package org.abalong.controller.daos;


import org.abalong.model.entities.Field;
import org.abalong.model.entities.Option;
import org.abalong.model.entities.Profile;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Instances of this class allow all CRUD operations whith {@code Field} entities.
 */
public class FieldsDao extends AbstractDao {

    /**
     * Saves {@code Field} instance in the database
     * @param field
     */
    public void save(Field field) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.save(field);
        transaction.commit();
        session.close();
    }

    /**
     * Updates {@code Field} instance in the database
     * @param field
     */
    public void update(Field field) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.merge(field);
        transaction.commit();
        session.close();
    }

    /**
     * Removes {@code Field} instance from the database
     * @param field
     */
    public void delete(Field field) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.delete(field);
        transaction.commit();
        session.close();
    }

    /**
     *
     * @param label represents label of the field
     * @return Field instance with such label
     */
    public Field getField(String label) {
        Session session = checkSession();
        Field field = session.get(Field.class, label);
        session.close();
        return field;
    }

    public boolean hasField(String label) {
        return getField(label) != null;
    }

    public List<Field> getAllFields() {
        Session session = checkSession();
        List<Field> field = session.createQuery("from Field ").getResultList();
        session.close();
        return field;
    }

    public List<Option> getOptions(String label) {
        Field field = getField(label);
        return field.getOptions();
    }
}
