package co.edu.uniandes.mati.service.domain.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
public class User {
	private Long idUser;
	private String email;
}
