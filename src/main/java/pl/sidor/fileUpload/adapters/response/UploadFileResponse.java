package pl.sidor.fileUpload.adapters.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UploadFileResponse {

    String fileName;
    String fileType;
    String fileUri;
    long size;
}
