package co.edu.uniandes.mati.service.infrastructure.repository;

import co.edu.uniandes.mati.service.domain.entity.PaymentCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentCheck,Long> {
}
