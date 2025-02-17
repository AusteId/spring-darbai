package lt.techin.catcafe.repository;

import lt.techin.catcafe.model.CatAdoption;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CatAdoptionRepository extends JpaRepository<CatAdoption, Long> {

  List<CatAdoption> findByUserId(long userId);

  Optional<CatAdoption> findByIdAndUserId(long id, long userId);
}
