package ms.igrey.dev.msvideo.api;

import feign.Param;
import feign.RequestLine;

public interface OmdbFeign {
    String OMDB_API_KEY = "f30f1c99";

    @RequestLine("GET /?apikey=" + OMDB_API_KEY + "&t={t}&{y}=y")
    String findByTitle(@Param("t") String filmTitle, @Param("y") String year);
}
