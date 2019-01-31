package ms.igrey.dev.msvideo.domain.srt;

import lombok.Data;
import lombok.experimental.Accessors;
import ms.igrey.dev.msvideo.dto.OmdbFilmDto;

import java.util.List;

@Data
@Accessors(fluent = true)
public class OmdbFilms {
    private List<OmdbFilmDto> film;

}
