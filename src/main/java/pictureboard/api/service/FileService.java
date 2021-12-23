package pictureboard.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pictureboard.api.domain.Img;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    public Img storeFile(MultipartFile multipartFile, String path) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        String fullPath = getFullPath(path, storeFileName);

        multipartFile.transferTo(new File(fullPath));
        return new Img(originalFileName, storeFileName, fullPath);
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

    public String getFullPath(String path, String storeFileName) {
        return path + storeFileName;
    }
}
