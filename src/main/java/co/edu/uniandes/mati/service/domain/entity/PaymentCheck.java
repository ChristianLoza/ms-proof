package co.edu.uniandes.mati.service.domain.entity;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentCheck {
    @Id
    @GeneratedValue
    private Long id;
    private Long idTest;
    private Boolean status;
    private Date paymentDate;
}
