package co.edu.uniandes.mati.service.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailAlert {
    private String event_type;
    private String contact;
    private Integer score;
}
