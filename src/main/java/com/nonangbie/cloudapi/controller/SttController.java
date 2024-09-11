package com.nonangbie.cloudapi.controller;

import com.nonangbie.cloudapi.service.SpeechToTextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("stt")
@RequiredArgsConstructor // 생성자 인젝션을 위한 어노테이션
public class SttController {

    private final SpeechToTextService sttService;

    /**
     * 녹음 파일을 받아서 텍스트로 변환하여 반환
     *
     * @param audioFile 오디오 파일
     * @return 녹음 파일을 변환한 텍스트
     */
    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleAudioMessage(@RequestParam("audioFile") MultipartFile audioFile) throws IOException {

        String transcribe = sttService.transcribe(audioFile);

        log.info("Received file: {}", audioFile.getOriginalFilename());
        log.info("File size: {} bytes", audioFile.getSize());
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(audioFile.getOriginalFilename());

        return ResponseEntity.ok().body(transcribe);
    }
}
