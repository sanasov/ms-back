package ms.igrey.dev.msvideo.ffmpeg;

import ms.igrey.dev.msvideo.FSPath;
import ms.igrey.dev.msvideo.domain.VideoInterval;
import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.bramp.ffmpeg.FFmpeg.FPS_24;

public class VideoCutter {

    private final FFmpegExecutor executor;

    public VideoCutter() {
        try {
            FFmpeg ffmpeg = new FFmpeg("C:\\programs\\FFMPEG\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("C:\\programs\\FFMPEG\\bin\\ffprobe.exe");
            this.executor = new FFmpegExecutor(ffmpeg, ffprobe);
        } catch (IOException e) {
            throw new RuntimeException("Could not init VideoCutter", e);
        }
    }


    public void cutIntoParts(String movieTitle, Subtitles subtitles, Integer partsAmount) {
        new File(FSPath.CUT_MOVIE_PATH + "/" + movieTitle).mkdirs();
        VideoInterval interval = new VideoInterval(subtitles.subtitles().size(), partsAmount);
        for (int i = 1; i <= partsAmount; i++) {
            System.out.println(subtitles.subtitles().get(interval.startNumSeq(i)).startOffset());
            System.out.println(subtitles.subtitles().get(interval.endNumSeq(i)).endOffset());
            executor.createJob(
                    cutPartFFMPEGBuilder(
                            findMovieFile(movieTitle),
                            i,
                            subtitles.subtitles().get(interval.startNumSeq(i)).startOffset(),
                            subtitles.subtitles().get(interval.endNumSeq(i)).endOffset()
                    )
            ).run();
        }
    }


    public void cut(String movieTitle, List<Subtitle> subtitles, Integer partNumber) {
        new File(FSPath.CUT_MOVIE_PATH + "/" + movieTitle).mkdirs();
        List<FFmpegBuilder> builders = subtitles.stream()
                .map(subtitle -> cutFragmentFFMPEGBuilder(
                        new File(FSPath.CUT_MOVIE_PATH + "/" + movieTitle + "/part" + partNumber + ".mp4"),
                        subtitle.numberSeq(),
                        subtitle.startOffset(),
                        subtitle.duration()
                ))
                .collect(Collectors.toList());
        for (FFmpegBuilder builder : builders) {
            executor.createJob(builder).run();
        }
    }

    private File findMovieFile(String movieTitle) {
        return FileUtils.listFiles(new File(FSPath.MOVIE_STORAGE_PATH), null, false).stream()
                .filter(file -> file.getName().contains(movieTitle))
                .findAny()
                .orElseThrow(() -> new RuntimeException("There is no movie " + movieTitle + " in movie storage"));
    }


    private void removeMoviePartFiles(String movieTitle) {
        FileUtils.listFiles(new File(FSPath.CUT_MOVIE_PATH + "/" + movieTitle), null, false).stream()
                .filter(file -> file.getName().contains("part"))
                .forEach(file -> file.delete());
    }

    /**
     * Делим фильм на несколько частей. А эти части делим потом на фрогменты
     * Здесь удаляется русский дубляж
     */
    private FFmpegBuilder cutPartFFMPEGBuilder(File movieFile, Integer partNumber, Long startOffset, Long duration) {
        return new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(movieFile.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(FSPath.CUT_MOVIE_PATH + "/" + FilenameUtils.removeExtension(movieFile.getName()) + "/part" + partNumber + ".mp4")
                .addExtraArgs("-map", "0:0")
                .addExtraArgs("-map", "0:2")       //удаляется русская аудиодорожка, как правило, она первая среди аудиодорожек
                .setFormat("mp4")

                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)

                .setVideoFrameRate(FPS_24)
                .setVideoResolution(720, 480)

                .setStartOffset(startOffset, TimeUnit.MILLISECONDS)
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
    }

    private FFmpegBuilder cutFragmentFFMPEGBuilder(File movieFile, Integer fragmentNumber, Long startOffset, Long duration) {
        return new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(movieFile.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(FSPath.CUT_MOVIE_PATH + "/" + FilenameUtils.removeExtension(movieFile.getName()) + "/" + fragmentNumber + ".mp4")
                .addExtraArgs("-map", "0:0")
                .addExtraArgs("-map", "0:1")
                .setFormat("mp4")

                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)

                .setVideoFrameRate(FPS_24)
                .setVideoResolution(720, 480)

                .setStartOffset(startOffset, TimeUnit.MILLISECONDS)
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
    }


    //  Сделан для эксперимента. Можно удалить со временем
    private void split() throws IOException {
        File file = ResourceUtils.getFile("C:\\Users\\MI\\Desktop\\films\\Vertigo.avi");
        File fileSrt = ResourceUtils.getFile("C:\\Users\\MI\\Desktop\\films\\Vertigo (1958).srt");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .addExtraArgs("-vf subtitles=" + fileSrt.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput("C:\\Users\\MI\\Desktop\\films\\VertigoPart1" + ".mp4")
                .setFormat("mp4")
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)
                .setVideoFrameRate(24, 1)
                .setStartOffset(600, TimeUnit.SECONDS)
                .setDuration(5, TimeUnit.SECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
//        System.out.println(builder.build());
        executor.createJob(builder).run();
    }

    //  Сделан для эксперимента. Можно удалить со временем
    private void removeRusAudioTrack() throws FileNotFoundException {
        File file = ResourceUtils.getFile("C:\\Users\\MI\\movieHero\\movieStorage\\Back to the Future (1985).mkv");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput("C:\\Users\\MI\\movieHero\\movieStorage\\Back to the Future (eng).mp4")
                .setFormat("mp4")
                .addExtraArgs("-map", "0:0")
                .addExtraArgs("-map", "0:2")
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)
                .setVideoFrameRate(FPS_24)
//                .setVideoCodec("libx264")
                .setVideoResolution(720, 480)
                //.setVideoFilter("scale=320:trunc(ow/a/2)*2")
                //.setVideoPixelFormat("yuv420p")
                //.setVideoBitStreamFilter("noise")
//                .setVideoQuality(2)
                .setStartOffset(600, TimeUnit.SECONDS)
                .setDuration(5, TimeUnit.SECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        executor.createJob(builder).run();
    }

    public static void main(String[] args) throws IOException {
        new VideoCutter().removeRusAudioTrack();
    }
}
