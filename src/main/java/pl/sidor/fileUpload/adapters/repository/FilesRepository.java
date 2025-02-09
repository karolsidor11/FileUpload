package pl.sidor.fileUpload.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.fileUpload.domain.model.Files;

import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    Optional<Files> findByFileName(String fileName);
}
