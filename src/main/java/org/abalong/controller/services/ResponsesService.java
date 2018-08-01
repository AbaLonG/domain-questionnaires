package org.abalong.controller.services;

import org.abalong.controller.daos.ResponseDao;
import org.abalong.model.entities.Answer;
import org.abalong.model.entities.Response;

import java.util.List;

/**
 * Instances of this class are used to operate with {@code Response} instances in the database throw {@code ResponseDao}.
 */
public class ResponsesService {

    private ResponseDao dao = new ResponseDao();

    /**
     * Indicates whether there is such response in the database
     * @param id id of the {@code Response} instance
     * @return
     */
    private boolean exists(long id) {
        return dao.getResponse(id) != null;
    }

    public boolean save(Response response) {
        if (exists(response.getId()))
            return false;

        dao.save(response);
        return true;
    }

    public boolean update(Response response) {
        if (!exists(response.getId()))
            return false;

        dao.update(response);
        return true;
    }

    public boolean delete(Response response) {
        if (!exists(response.getId()))
            return false;

        dao.delete(response);
        return true;
    }

    /**
     *
     * @return All responses that database contains.
     */
    public List<Response> getAllResponses() {
        return dao.getAllResponses();
    }
}
