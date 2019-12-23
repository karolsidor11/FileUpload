package pl.sidor.fileUpload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sidor.fileUpload.model.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, String> {
}
