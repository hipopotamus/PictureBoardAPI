package pictureboard.api.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Img {

    private String fileName;

    private String storeFileName;

    private String fullPath;

    protected Img() {
    }

    public Img(String fileName, String storeFileName, String fullPath) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
        this.fullPath = fullPath;
    }
}
