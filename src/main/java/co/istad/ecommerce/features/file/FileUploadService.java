package co.istad.ecommerce.features.file;


import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile file);
    List<FileUploadResponse> uploadMultipleFile(List<MultipartFile> files);
    void deleteFile(String name);

    Page<FileUploadResponse> getAllFiles(PageRequest pageRequest);

    FileUploadResponse findByName(String name);
}
