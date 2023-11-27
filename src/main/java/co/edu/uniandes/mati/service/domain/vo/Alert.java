package co.edu.uniandes.mati.service.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Alert {
    private String contact;
    private Integer score;
}
