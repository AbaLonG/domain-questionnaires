package org.abalong.model.entities;

/**
 * This class represents a field type of the field's entity.
 */
public enum FieldType {

    SINGLE_LINE_TEXT,
    MULTI_LINE_TEXT,
    RADIO_BUTTON,
    CHECK_BOX,
    COMBO_BOX,
    DATE;

    /**
     *
     * @param type contains string representation of the {@code FieldType} value
     * @return a FieldType instance which matches the string representation.
     */
    public static FieldType getType(String type) {
        switch (type) {
            case "Single line text":
                return FieldType.SINGLE_LINE_TEXT;
            case "Multiline text":
                return FieldType.MULTI_LINE_TEXT;
            case "Combobox":
                return FieldType.COMBO_BOX;
            case "Checkbox":
                return FieldType.CHECK_BOX;
            case "Radio button":
                return FieldType.RADIO_BUTTON;
            case "Date":
                return FieldType.DATE;
            default:
                return null;
        }
    }

    /**
     *
     * @param fieldType has to be converted to the string representation of the {@code FieldType} value
     * @return String representation of the {@code FieldType} value.
     */
    public static String stringType(FieldType fieldType) {
        switch (fieldType) {
            case DATE:
                return "Date";
            case CHECK_BOX:
                return "Check box";
            case COMBO_BOX:
                return "Combo box";
            case RADIO_BUTTON:
                return "Radio button";
            case MULTI_LINE_TEXT:
                return "Multi line text";
            case SINGLE_LINE_TEXT:
                return "Single line text";
            default:
                return "";
        }
    }
}
