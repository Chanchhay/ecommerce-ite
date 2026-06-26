package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    FileUpload findByName(String name);
    void deleteByName(String name);
}
