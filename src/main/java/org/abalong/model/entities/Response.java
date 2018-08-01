package org.abalong.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a response by somebody who filled in form on main page of the application.<br>
 * Instances of this class contain unique {@code id} and list of the {@code answers}.
 */
@Entity
@Table(name = "responses")
public class Response {

    private long id;
    private List<Answer> answers = new ArrayList<Answer>();

    public Response() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    /**
     * One-To-Many relation to the {@code Answers} table in the database.
     * Removing an entity of this class removes all options related to the instance.
     * @return
     */
    @OneToMany(targetEntity = Answer.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "response", orphanRemoval = true)
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answer.setResponse(this);
        answers.add(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return id == response.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
