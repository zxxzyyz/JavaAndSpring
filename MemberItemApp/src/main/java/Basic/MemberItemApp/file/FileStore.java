package Basic.MemberItemApp.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public Map<String, String> storeFile(MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                return null;
            }

            HashMap<String, String> filenames = new HashMap<>();
            String originalFilename = multipartFile.getOriginalFilename();
            String storeFileName = createStoreFileName(originalFilename);
            filenames.put("originalFilename", originalFilename);
            filenames.put("storedFilename", storeFileName);
            multipartFile.transferTo(new File(getFullPath(storeFileName)));

            return filenames;
        } catch (IOException ex) {
            return new HashMap<>();
        }
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
