package co.edu.uniandes.mati.service.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;



@Data
public class Payment{
    private Long idPayment;
    private Long idTest;
    private BigDecimal amount;
    private Boolean status;
    private User user;
    private String typePayment;
    private Date paymentDate;
}
