package com.nonangbie.S3.config;

import com.amazonaws.regions.Regions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class S3Config {
    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String accessSecret;
//    @Value("${cloud.aws.region.static}")
//    private String region;

    @Bean
    public AmazonS3 s3Client() {
        logger.info("Initializing S3 client with region: ap-northeast-2");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        logger.info("S3 client initialized successfully");
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName("ap-northeast-2"))  // 동적 리전 설정
                .build();
    }
}
