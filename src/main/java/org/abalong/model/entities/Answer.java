package org.abalong.model.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a unique answer to a field by a response entity.<br>
 * It contains unique id of the answer, label of the field, response, and answer
 */
@Entity
@Table(name="answers")
public class Answer {

    private long id;
    private String label;
    private String answer;
    private Response response;

    public Answer() {

    }

    public Answer(String label, String answer) {
        this.label = label;
        this.answer = answer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    /**
     * Many-To-One relation to a {@code Response} entity. One response may has many answers
     * @return
     */
    @ManyToOne(targetEntity = Response.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "response_id", foreignKey = @ForeignKey(name = "response_id_fk"), nullable = false)
    public Response getResponse() {
        return response;
    }

    /**
     * Label of a {@code Field}
     * @return
     */
    @Column(name = "field", nullable = false)
    public String getLabel() {
        return label;
    }

    @Column(nullable = false)
    public String getAnswer() {
        return answer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
