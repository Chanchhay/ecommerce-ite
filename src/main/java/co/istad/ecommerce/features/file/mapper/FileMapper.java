package co.istad.ecommerce.features.file.mapper;

import co.istad.ecommerce.features.file.FileUpload;
import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileUploadResponse mapToFileUploadResponse(FileUpload fileUpload);
}
