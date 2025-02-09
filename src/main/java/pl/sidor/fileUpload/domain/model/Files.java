package pl.sidor.fileUpload.domain.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files implements Serializable {
    private static final long serialVersionUID = 1217891551922389709L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Lob
    @Column(name = "FILE")
    private byte[] file;

    public Files(String fileName, String fileType, byte[] file) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Files files = (Files) o;
        return fileName.equals(files.fileName) &&
                fileType.equals(files.fileType) &&
                Arrays.equals(file, files.file);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName, fileType);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
