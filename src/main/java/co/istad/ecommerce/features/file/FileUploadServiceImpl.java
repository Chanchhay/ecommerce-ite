package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import co.istad.ecommerce.features.file.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileUploadRepository fileUploadRepository;
    private final FileMapper fileMapper;
    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        return save(file);
    }

    @Override
    public List<FileUploadResponse> uploadMultipleFile(List<MultipartFile> files) {
        return files.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(String name) {
        Path path = Paths.get(storageLocation + name);

        FileUpload fileUpload = fileUploadRepository.findByName(name);
        if (fileUpload == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File has not been found");

        fileUploadRepository.deleteByName(name);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File has been failed to delete");
        }
    }

    @Override
    public Page<FileUploadResponse> getAllFiles(PageRequest pageRequest) {
        Page<FileUpload> fileUploads = fileUploadRepository.findAll(pageRequest);
        return fileUploads.map(fileMapper::mapToFileUploadResponse);
    }

    @Override
    public FileUploadResponse findByName(String name) {
        return fileMapper.mapToFileUploadResponse(fileUploadRepository.findByName(name));
    }

    private FileUploadResponse save(MultipartFile file) {
        String ext = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String name = UUID.randomUUID().toString();
        FileUpload newFile = new FileUpload();
        newFile.setName(name);
        newFile.setSize(file.getSize());
        newFile.setExtension(ext);
        newFile.setCaption("-- FILE UPLOAD -- DEMO");
        newFile.setMediaType(file.getContentType());
        newFile.setUri(fileBaseUri + name + ext);

        fileUploadRepository.save(newFile);

        Path path = Paths.get(storageLocation + "/" + name + ext);

        log.info("nameeee::::   {}",name);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File has been failed to upload");
        }
        return fileMapper.mapToFileUploadResponse(newFile);
    }
}
