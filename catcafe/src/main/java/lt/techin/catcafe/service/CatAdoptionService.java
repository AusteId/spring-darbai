package lt.techin.catcafe.service;

import lt.techin.catcafe.model.CatAdoption;
import lt.techin.catcafe.repository.CatAdoptionRepository;
import lt.techin.catcafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatAdoptionService {

  private final CatAdoptionRepository catAdoptionRepository;
  private final UserRepository userRepository;

  @Autowired
  public CatAdoptionService(CatAdoptionRepository catAdoptionRepository, UserRepository userRepository) {
    this.catAdoptionRepository = catAdoptionRepository;
    this.userRepository = userRepository;
  }

  public CatAdoption saveCatAdoption(CatAdoption catAdoption) {
    return catAdoptionRepository.save(catAdoption);
  }

  public List<CatAdoption> findAllCatAdoptions() {
    return catAdoptionRepository.findAll();
  }

  public Optional<CatAdoption> findCatAdoptionById(long id) {
    return catAdoptionRepository.findById(id);
  }

  public List<CatAdoption> findCatAdoptionsByUserId(long userId) {
    return catAdoptionRepository.findByUserId(userId);
  }

  public CatAdoption updateCatAdoptionStatus(long id, String status) {

    Optional<CatAdoption> existingCatAdoption = catAdoptionRepository.findById(id);

    if (existingCatAdoption.isEmpty()) {
      return null;
    }

    CatAdoption catAdoption = existingCatAdoption.get();
    catAdoption.setStatus(status);

    return catAdoptionRepository.save(catAdoption);
  }

  public CatAdoption findById(long id) {
    return catAdoptionRepository.findById(id).get();
  }

}
