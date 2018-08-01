package org.abalong.controller.daos;

import org.abalong.model.entities.Answer;
import org.abalong.model.entities.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Instances of this class allow all CRUD operations whith {@code Response} entities.
 */
public class ResponseDao extends AbstractDao {

    public void save(Response response) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.save(response);
        transaction.commit();
        session.close();
    }

    public void update(Response response) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.merge(response);
        transaction.commit();
        session.close();
    }

    public void delete(Response response) {
        Session session = checkSession();
        Transaction transaction = session.beginTransaction();
        session.delete(response);
        transaction.commit();
        session.close();
    }

    /**
     *
     * @param id of the response
     * @return a response with such {@code id}
     */
    public Response getResponse(long id) {
        Session session = checkSession();
        Response response = session.get(Response.class, id);
        session.close();
        return response;
    }

    public boolean hasResponse(long id) {
        return getResponse(id) != null;
    }

    /**
     *
     * @return returns all responses that database contains in a {@code List} form.
     */
    public List<Response> getAllResponses() {
        Session session = checkSession();
        return session.createQuery("from Response ").getResultList();
    }
}
