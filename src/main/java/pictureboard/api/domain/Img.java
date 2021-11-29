package pictureboard.api.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Img {

    private String fileName;

    private String storeFileName;

    protected Img() {
    }

    public Img(String fileName, String storeFileName) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
    }
}
