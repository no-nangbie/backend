package com.nonangbie.cloudapi.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class SpeechToTextService {

    public String transcribe(MultipartFile audioFile) throws IOException {
        // 오디오 파일을 바이트 배열로 변환
        byte[] audioBytes = audioFile.getBytes();

        try (SpeechClient speechClient = SpeechClient.create()) {
            // 오디오 데이터를 설정
            RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(audioBytes))
                    .build();

            // 설정 객체 생성
            RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC) // FLAC 형식의 오디오 파일을 처리
                    .setSampleRateHertz(16000) // 샘플레이트 설정
                    .setLanguageCode("ko-KR") // 한국어로 설정
                    .build();

            // API를 통해 음성을 텍스트로 변환
            RecognizeResponse response = speechClient.recognize(recognitionConfig, recognitionAudio);
            SpeechRecognitionResult result = response.getResultsList().get(0);
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

            return alternative.getTranscript(); // 변환된 텍스트 반환
        }
    }
}
