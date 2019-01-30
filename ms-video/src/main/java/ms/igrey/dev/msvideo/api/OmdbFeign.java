package ms.igrey.dev.msvideo.api;

import feign.Param;
import feign.RequestLine;

public interface OmdbFeign {

    @RequestLine("GET /?apikey=f30f1c99&t={t}&{y}=y")
    String findByTitle(@Param("t") String filmTitle, @Param("y") String year);
}
