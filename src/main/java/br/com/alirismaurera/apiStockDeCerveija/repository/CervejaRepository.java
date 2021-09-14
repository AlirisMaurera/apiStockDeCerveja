package br.com.alirismaurera.apiStockDeCerveija.repository;


import br.com.alirismaurera.apiStockDeCerveija.entity.Cerveja;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CervejaRepository extends JpaRepository<Cerveja, Long> {

   Optional<Cerveja> findByNome(String name);
}
