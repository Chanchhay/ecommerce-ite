package co.istad.ecommerce.features.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 10)
    private String extension;

    private String caption;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String mediaType;
    @Column(nullable = false)
    private String uri;
}
