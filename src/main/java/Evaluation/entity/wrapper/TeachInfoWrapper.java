package Evaluation.entity.wrapper;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class TeachInfoWrapper {
    private Integer id;

    @NotBlank
    private String position;
    @NotBlank
    private String date;
    @NotBlank
    private String tid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
