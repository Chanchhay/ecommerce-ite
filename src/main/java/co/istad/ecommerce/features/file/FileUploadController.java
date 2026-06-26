package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/files")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileUploadResponse uploadFile(@RequestPart MultipartFile file){
        return fileUploadService.upload(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/multiple")
    public List<FileUploadResponse> uploadFile(@RequestPart List<MultipartFile> files){
        return fileUploadService.uploadMultipleFile(files);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteFile(@RequestParam String name){
        fileUploadService.deleteFile(name);
    }

    @GetMapping("/{name}")
    public FileUploadResponse getByName(@PathVariable String name){
        return fileUploadService.findByName(name);
    };

    @GetMapping
    public Page<FileUploadResponse> getAllFiles(@RequestParam(defaultValue = "0") int pageNum, @RequestParam(defaultValue =
    "5") int pageSize){
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sortById);
        return fileUploadService.getAllFiles(pageRequest);
    }
}
