package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.domain.srt.Subtitle;

import java.io.File;

public interface MovieRepository {

    File findMovie(Subtitle subtitle);

}
