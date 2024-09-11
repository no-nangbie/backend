package com.nonangbie.cloudapi.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

@RestController
@RequestMapping("/api/stt")
public class SttController {

    @PostMapping("/recognize")
    public String recognizeSpeech(@RequestParam("file") MultipartFile file) {
        try {
            // 파일을 바이트 배열로 변환
            byte[] bytes = file.getBytes();
            ByteString audioBytes = ByteString.copyFrom(bytes);

            // STT 요청을 위한 오디오 구성
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // 인식 구성 설정
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                    .setSampleRateHertz(48000)
                    .setLanguageCode("ko-KR")
                    .build();

            // Google Speech-to-Text 호출
            try (SpeechClient speechClient = SpeechClient.create()) {
                RecognizeResponse response = speechClient.recognize(config, audio);
                StringBuilder transcription = new StringBuilder();
                for (SpeechRecognitionResult result : response.getResultsList()) {
                    transcription.append(result.getAlternativesList().get(0).getTranscript());
                }
                return transcription.toString();
            }

        } catch (Exception e) {
            return "오디오 파일 처리 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
