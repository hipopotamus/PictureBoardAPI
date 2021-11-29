package pictureboard.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.Img;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FileService {

    public Img storeFile(MultipartFile multipartFile, String path) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(getPullPath(path, storeFileName)));
        return new Img(originalFileName, storeFileName);
    }

    private String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extracted(originalFileName);
        return uuid + "." + ext;
    }

    private String extracted(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    public String getPullPath(String path, String storeFileName) {
        return path + storeFileName;
    }
}
