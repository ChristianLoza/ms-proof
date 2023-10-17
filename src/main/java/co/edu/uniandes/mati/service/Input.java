package co.edu.uniandes.mati.service;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Input {
    @Id
    @GeneratedValue
    private Long id;

    private String requestType;
    private String technology;
    private String requestdBy;
    private Integer amountOfQuestion;
    private Date date;
}
