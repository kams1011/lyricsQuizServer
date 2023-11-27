package kr.toy.lyricsQuizServer.file.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@AllArgsConstructor
public enum FileExtension {


    WMV(new int[]{48, 38, 178, 117, 142, 102, 207, 17, 166, 217, 0, 170, 0, 98, 206, 108}, "video/x-ms-wmv"), // 30 26 B2 75 8E 66 CF 11 A6 D9 00 AA 00 62 CE 6C
    WMA(new int[]{48, 38, 178, 117, 142, 102, 207, 17, 166, 217, 0, 170, 0, 98, 206, 108}, "audio/x-ms-wma"), // 30 26 B2 75 8E 66 CF 11 A6 D9 00 AA 00 62 CE 6C
    MOV(new int[]{0, 0, 0, 20, 102, 116, 121, 112, 113, 116, 32, 32}, "video/quicktime"), // 0x66 0x74 0x79 0x70
    MPEG1(new int[]{0, 0, 1, -70}, "video/mpeg"), // 00 00 01 BA
    MPEG2(new int[]{0, 0, 1, 186}, "video/mpeg"), // 00 00 01 BA
    MP3(new int[]{73, 68, 51}, "audio/mpeg"), // 또는 255 240
    MP4(new int[]{102, 116, 121, 112, 77, 83, 78, 86}, "video/mp4"),  // 0x66 0x74 0x79 0x70 // 기타 다른 mp4파일 유형도 존재. 파일 박스로 구별하는걸로 보임.
    AVI(new int[]{82, 73, 70, 70, 0, 0, 0, 0, 65, 86, 73, 32, 76, 73, 83, 84}, "video/x-msvideo"); // 0x52 0x49 0x46 0x46 0x41 0x56 0x49 0x20
    // FIXME AVI는 앞의 4개의 16진수 뒤에 4개가 파일 사이즈를 결정해서 파일별로 다르다. 0 0 0 0 부분을 처리할 수 있도록 로직을 변경.

    // 파일 사이즈를 확인한다. -> MultipartFile config로 해결.
    // 먼저 ContentType을 확인한다. -> 해결.
    // 파일 시그니처를 확인한다.

    private final int[] code;
    private final String type;


    public boolean checkAVIFileSignature(MultipartFile file) throws IOException {
        boolean valid = true;
        for (int i=0; i<file.getBytes().length; i++) {
            if(i > 3 && i < 8) {
                continue;
            }
            if(file.getBytes()[i] != AVI.getCode()[i]){
                valid = false;
            }
        }
        return valid;
    }

}
