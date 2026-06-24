package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        // prepare file information eg. file name
        String name = UUID.randomUUID().toString();
        name = getExtAndUpload(file, name);
        return FileUploadResponse.builder()
                .name(name).size(file.getSize()).mediaType(file.getContentType()).uri(fileBaseUri + "/" + name).build();
    }

    @Override
    public List<FileUploadResponse> uploadMultipleFile(MultipartFile[] files) {

        List<FileUploadResponse> fileUploadResponseList = new ArrayList<>();

        for (MultipartFile file : files) {
            // prepare file information eg. file name
            String name = UUID.randomUUID().toString();
            name = getExtAndUpload(file, name);
            fileUploadResponseList.add(FileUploadResponse.builder()
                    .name(name)
                    .size(file.getSize())
                    .mediaType(file.getContentType())
                    .uri(fileBaseUri + "/" + name)
                    .build());
        }

        return fileUploadResponseList;
    }

    @Override
    public void deleteFile(String name) {
        Path path = Paths.get(storageLocation + name);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File has been failed to delete");
        }
    }

    private String getExtAndUpload(MultipartFile file, String name) {
        String ext = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));

        name += ext;

        Path path = Paths.get(storageLocation + name);
        System.out.println(path);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File has been failed to upload");
        }

        return name;
    }
}
