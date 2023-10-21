package co.edu.uniandes.mati.service.infrastructure.repository;

import co.edu.uniandes.mati.service.domain.entity.Input;
import io.smallrye.common.annotation.Blocking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input,Long> {
}
