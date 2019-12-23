package pl.sidor.fileUpload.upload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {

    private final String fileName;
    private final String fileType;
    private final String fileUri;
    private final long size;
}
