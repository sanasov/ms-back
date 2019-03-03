package ms.igrey.dev.msvideo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VideoInfoDto {
    private Integer numSeq;
    private String filmId;
    private List<String> lines;

    public VideoInfoDto(Integer numSeq, String filmId, List<String> lines) {
        this.numSeq = numSeq;
        this.filmId = filmId;
        this.lines = lines;
    }
}
