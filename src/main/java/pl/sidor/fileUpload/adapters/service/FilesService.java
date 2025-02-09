package pl.sidor.fileUpload.adapters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.sidor.fileUpload.domain.ports.FilesPorts;
import pl.sidor.fileUpload.exception.FileStorageException;
import pl.sidor.fileUpload.domain.model.Files;
import pl.sidor.fileUpload.adapters.repository.FilesRepository;
import pl.sidor.fileUpload.exception.MessageException;
import pl.sidor.fileUpload.utils.StringUtil;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class FilesService implements FilesPorts {
    private final FilesRepository filesRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Files findById(final Long fileID) {
        return filesRepository.findById(fileID)
                .orElseThrow(() -> new FileStorageException(StringUtil.createMessage(MessageException.FILE_NOT_FOUND, fileID)));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Files findByFilename(final String fileName) {
        return filesRepository.findByFileName(fileName)
                .orElseThrow(() -> new FileStorageException(StringUtil.createMessage(MessageException.FILE_NOT_FOUND_BY_FILENAME, fileName)));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFile(final MultipartFile file) {
        if (!isNull(file)) {
            Files files = prepareFile(file);
            filesRepository.save(files);
        } else {
            throw new FileStorageException(MessageException.FILE_IS_NULL.getMessage());
        }
    }

    private Files prepareFile(final MultipartFile file) {
        String fileName = checkFilename(file);
        try {
            return new Files(fileName, file.getContentType(), file.getBytes());
        } catch (IOException e) {
            throw new FileStorageException(MessageException.INTERNAL_ERROR.getMessage());
        }
    }

    private String checkFilename(MultipartFile file) {
        String fileName = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new FileStorageException(MessageException.INVALID_FILENAME.getMessage());
        }
        return fileName;
    }
}
