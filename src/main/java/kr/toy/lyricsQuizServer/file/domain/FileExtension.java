package kr.toy.lyricsQuizServer.file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Getter
@AllArgsConstructor
public enum FileExtension {


    WMV(new int[]{48, 38, 178, 117, 142, 102, 207, 17, 166, 217, 0, 170, 0, 98, 206, 108}, "Microsoft Windows Media Audio/Video File"), // 30 26 B2 75 8E 66 CF 11 A6 D9 00 AA 00 62 CE 6C
    WMA(new int[]{48, 38, 178, 117, 142, 102, 207, 17, 166, 217, 0, 170, 0, 98, 206, 108}, "Microsoft Windows Media Audio/Video File"), // 30 26 B2 75 8E 66 CF 11 A6 D9 00 AA 00 62 CE 6C
    MOV(new int[]{102, 116, 121, 112}, "1234"), // 0x66 0x74 0x79 0x70
    MPG(new int[]{0, 0, 1, 186}, "1234"), //  00 00 01 BA
    MPEG(new int[]{0, 0, 1, 186}, "1234"), // 00 00 01 BA
    MP3(new int[]{73, 68, 51}, "1234"), // 0x49 0x44 0x33
    MP4(new int[]{102, 116, 121, 112}, "1234"),  // 0x66 0x74 0x79 0x70
    AVI(new int[]{82, 73, 70, 70, 65, 86, 73, 32}, "1234"); // 0x52 0x49 0x46 0x46 0x41 0x56 0x49 0x20

    // 먼저 ContentType을 확인한다.
    // 파일 사이즈를 확인한다.
    // 확장자를 확인한다.
    // 파일 시그니처를 확인한다.



    private final int[] code;
    private final String type;



    public boolean isVideo(MultipartFile file) throws IOException {
        
        byte[] resultBytes = new byte[20];

        try (InputStream inputStream = file.getInputStream()){
            int bytesRead  = inputStream.read(resultBytes, 0, 20);
            if (bytesRead == -1) {
                throw new IOException("End of file reached before reading the specified length.");
            }
        }


        return true;
    }



}
