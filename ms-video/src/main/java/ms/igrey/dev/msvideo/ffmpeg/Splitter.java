package ms.igrey.dev.msvideo.ffmpeg;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Splitter {
    public static void main(String[] args) throws IOException {
        split();
    }

    private static void split() throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:\\programs\\FFMPEG\\bin\\ffmpeg.exe");
        FFprobe ffprobe = new FFprobe("C:\\programs\\FFMPEG\\bin\\ffprobe.exe");
        File file = ResourceUtils.getFile("C:\\Users\\MI\\Desktop\\films\\Vertigo.avi");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput("C:\\Users\\MI\\Desktop\\films\\VertigoPart1" + ".mp4")
                .setFormat("mp4")
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)
                .setVideoFrameRate(24, 1)
                .setStartOffset(600, TimeUnit.SECONDS)
                .setDuration(5,TimeUnit.SECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }
}
