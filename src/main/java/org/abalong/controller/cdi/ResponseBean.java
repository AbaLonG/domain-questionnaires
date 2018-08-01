package org.abalong.controller.cdi;

import org.abalong.controller.services.FieldsService;
import org.abalong.controller.services.ResponsesService;
import org.abalong.model.entities.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is CDI bean. It is used on {@code responses.xhtml} and {@code index.xhtml} pages.
 * It saves all the data which user added to the main questionnaires form in index.xhtml page.
 */
@Named
@RequestScoped
public class ResponseBean {

    private ResponsesService service;
    private FieldsService fieldsService;
    private List<Field> fields;
    private int size;

    /**
     * Fields with {@code SINGLE_LINE_TEXT}, {@code MULTI_LINE_TEXT}, {@code RADIO_BUTTON}, {@code COMBO_BOX}
     * types will be saves to this array.
     */
    private String[] textes;

    /**
     * Fields with {@code Date} type will be saved to this array
     */
    private Date[] dates;

    /**
     * Fields with {@code COMBO_BOX} type will be save to this array.
     */
    private String[][] checks;

    public ResponseBean() {

    }

    /**
     * This method initializes all fields after creating of {@code ResponseBean} instances.
     */
    @PostConstruct
    public void init() {
        service = new ResponsesService();
        fieldsService = new FieldsService();
        fields = fieldsService.getAllFields();
        size = fields.size();
        textes = new String[size];
        dates = new Date[size];
        checks = new String[size][];
    }

    public String[] getTextes() {
        return textes;
    }

    public void setTextes(String[] textes) {
        this.textes = textes;
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public String[][] getChecks() {
        return checks;
    }

    public void setChecks(String[][] checks) {
        this.checks = checks;
    }

    /**
     * This method checks that answer with field where {@code isRequired == true} has to not be empty.
     * @param field of quesion
     * @param value
     * @return true if field is required and value is not empty
     */
    public boolean requiredButEmpty(Field field, String value) {
        if (field.isRequired()) {
            if (StringUtils.isEmpty(value)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                String.format("Label %s is required!", field.getLabel())));
                return true;
            }
        }
        return false;
    }

    /**
     * This method saves responses to the database.
     *
     * !!!IMPORTANT!!!
     * I don't know why, but you can only save last value of fileds if
     * type of the field is radio button or check box or combo box.
     * This method invokes only when you choosed only last value of field with such types.
     *
     * <br><br>
     * На русском:
     * При заполнении формы на странице index.xhtml, после нажатия на кнопку подтверждения,
     * этот метод не вызывается если в анкете были поля с типами radio button, check box, combo box и в них
     * были заданы не последние значения.
     * Метод работает если в полях этих типов были выбраны только последние значения,
     * я думаю это баг JSF связаный с использованием "f:selectItems".
     * Если выбирать не последние значения, метод просто не вызывается.
     *
     * Однако, при использовании Single line text, multi line text, date и последних значений radio button, combo box, check box,
     * метод работает корректно.
     */
    public void sendResponse() {
        Response response = new Response();
        for (int i = 0; i < size; i++) {
            Field current = fields.get(i);
            switch (current.getFieldType()) {
                case SINGLE_LINE_TEXT:
                case MULTI_LINE_TEXT:
                case RADIO_BUTTON:
                case COMBO_BOX:
                    if (StringUtils.isEmpty(textes[i]))
                        continue;
                    if (requiredButEmpty(current, textes[i])) {
                        response = null;
                        return;
                    }
                    response.addAnswer(new Answer(current.getLabel(), textes[i]));
                    break;
                case DATE:
                    if (StringUtils.isEmpty(dates[i].toString()))
                        continue;
                    if (requiredButEmpty(current, dates[i].toString())) {
                        response = null;
                        return;
                    }
                    response.addAnswer(new Answer(current.getLabel(),
                            new SimpleDateFormat("MMM dd, yyyy").format(dates[i])));
                    break;
                case CHECK_BOX:
                    for (String a : checks[i])
                        response.addAnswer(new Answer(current.getLabel(), a));
                    break;
            }
        }

        if (!service.save(response))
            return;

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/success.xhtml");
        }
        catch (Exception ignored) {

        }
    }

    public List<Response> getResponses() {
        return service.getAllResponses();
    }


    /**
     * This method returns value of the field of response parameter.
     * If response has no answer with such label of field. it returns N/A.
     * @param response
     * @param label
     * @return
     */
    public String getAnswer(Response response, String label) {
        List<Answer> answerList = response.getAnswers();
        for (int i = 0; i < answerList.size(); i++) {
            Answer answer = answerList.get(i);
            if (answerList.get(i).getLabel().equals(label)) {
                return answer.getAnswer();
            }
        }
        return "N/A";
    }
}
