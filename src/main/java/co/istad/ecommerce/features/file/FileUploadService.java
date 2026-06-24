package co.istad.ecommerce.features.file;


import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile file);
    List<FileUploadResponse> uploadMultipleFile(MultipartFile[] files);
    void deleteFile(String name);
}
