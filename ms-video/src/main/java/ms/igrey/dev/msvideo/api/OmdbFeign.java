package ms.igrey.dev.msvideo.api;

import feign.Param;
import feign.RequestLine;

public interface OmdbFeign {

    @RequestLine("GET /")
    String findByTitle(@Param("t") String filmTitle, @Param("y") String year);
}
