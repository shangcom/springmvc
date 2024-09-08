package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody= {}", messageBody);

        response.getWriter().write("ok");

    }

    /**
     * inputStream과 Writer를 직접 생성하는 대신 Spring의 자동 주입 기능 사용.
     *
     * @param inputStream    request.getInputStream()으로 직접 가져오는 대신,
     *                       InputStream을 파라미터로 받으면 Spring이 자동으로 요청 바디의 데이터를 바이트 스트림으로 처리
     * @param responseWriter response.getWriter()로 가져오는 대신, Writer 객체로 처리
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody= {}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * @param httpEntity HTTP 요청/응답의 바디와 헤더 정보를 캡슐화한 객체.
     *                   요청 바디와 헤더 정보를 더 쉽게 다룰 수 있음.
     * @return HttpEntity를 return하면 view 조회하지 않고, http 응답 메시지에 설정한 body와 header를 넣어서 바로 보냄.
     * request는 RequestEntity, response는 ResponseEntity를 사용해도 된다.
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();
        log.info("messageBody= {}", messageBody);
        log.info("headers= {}", headers);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("header1", "111");

        return new HttpEntity<>("ok", httpHeaders);
    }

    /**
     * RequestBody, RequestHeader 어노테이션으로 request 메시지의 바디, 헤더 가져올 수 있음
     * @param headersMap 이건 전부 다 가져오기 위해서 MultiValueMap으로 받아봤음.
     * @return ResponseBody 통해 반환한 String을 응답 메시지 본문으로 바로 내보냄.
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody, @RequestHeader MultiValueMap<String, String> headersMap) {
        log.info("messageBody= {}, headers= {}", messageBody, headersMap);

        return "ok";
    }

}

