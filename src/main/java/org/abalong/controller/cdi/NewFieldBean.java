package org.abalong.controller.cdi;

import org.abalong.controller.services.FieldsService;
import org.abalong.model.entities.Field;
import org.abalong.model.entities.FieldType;
import org.abalong.model.entities.Option;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * This CDI bean is being used during creating of new {@code Field} entity
 * on {@code fields.xhtml} page after submitting {@code ADD FIELD} button.
 * It contains all values that {@code Field} entity do.
 */
@Named
@RequestScoped
public class NewFieldBean {

    private String label;
    private String stringType;
    private String fullOptions;

    private boolean required;
    private boolean isActive;

    private FieldsService service = new FieldsService();

    public NewFieldBean() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public String getFullOptions() {
        return fullOptions;
    }

    public void setFullOptions(String fullOptions) {
        this.fullOptions = fullOptions;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * This is main method of the {@code NewFieldBean} class.
     * It attempts to save created {@code Field} to the database.
     * It saves {@code Field} successfully if:
     *  <ul>
     *      <li>label is not empty</li>
     *      <li>type is not empty</li>
     *      <li>there is not such field in the database</li>
     *  </ul>
     */
    public void saveField() {
        if (StringUtils.isEmpty(label)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Label can not be empty."));
            return;
        }

        if (StringUtils.isEmpty(stringType)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Type can not be empty."));
            return;
        }

        FieldType type = FieldType.getType(stringType);
        if (type == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Type can not be empty"));
            return;
        }

        Field field = service.getField(label);
        if (field != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Such field already exists."));
            return;
        }

        field = new Field(label, type);
        field.setRequired(required);
        field.setActive(isActive);
        service.save(field);
        if (!StringUtils.isEmpty(fullOptions)) {
            String[] options = fullOptions.split("\n");
            for (String option : options)
                field.addOption(new Option(field, option));
            service.update(field);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/domainname/index.xhtml");
        }
        catch (Exception ignored) {
        }
    }
}
