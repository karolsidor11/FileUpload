package pl.sidor.fileUpload.adapters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.domain.ports.FilesPorts;
import pl.sidor.fileUpload.exception.FileStorageException;
import pl.sidor.fileUpload.domain.model.entity.Files;
import pl.sidor.fileUpload.adapters.repository.FilesRepository;
import pl.sidor.fileUpload.exception.MessageException;
import pl.sidor.fileUpload.utils.StringUtil;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class FilesService implements FilesPorts {

    private final FilesRepository filesRepository;

    @Autowired
    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Files findById(final Long fileID) {
        return filesRepository.findById(fileID)
                .orElseThrow(() -> new FileStorageException(StringUtil.createMessage(MessageException.FILE_NOT_FOUND, fileID)));
    }

    public Files findByFilename(final String fileName) {
        return filesRepository.findByFileName(fileName)
                .orElseThrow(() -> new FileStorageException(StringUtil.createMessage(MessageException.FILE_NOT_FOUND_BY_FILENAME, fileName)));

    }

    public void saveFile(final MultipartFile file) {
        if (!isNull(file)) {
            String fileName = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));
            saveFileInDatabase(file, fileName);
        } else {
            throw new FileStorageException(MessageException.FILE_IS_NULL.getMessage());
        }
    }

    private void saveFileInDatabase(MultipartFile file, String fileName) {
        try {
            checkFilename(fileName);
            Files dbFile = new Files(fileName, file.getContentType(), file.getBytes());
            filesRepository.save(dbFile);
        } catch (Exception e) {
            throw new FileStorageException(MessageException.INTERNAL_ERROR.getMessage());
        }
    }

    private void checkFilename(String fileName) {
        if (fileName.contains("..")) {
            throw new FileStorageException(MessageException.INVALID_FILENAME.getMessage());
        }
    }
}
