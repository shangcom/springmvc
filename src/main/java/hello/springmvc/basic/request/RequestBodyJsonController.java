package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * request message body에너 넘어오는 내용
 * {"username":hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody= {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {

        log.info("messageBody= {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * 요청 파라미테어세 @ModelAttribute로 자동 매핑 하는 것처럼,
     * '@RequestBody'에 직접 만든 객체를 지정할 수 있다.
     *
     * @param data 여기에 직접 만든 클래스를 사용하면 자동으로 매핑된다.
     *             '@RequestBody' 생략하면 안됨.
     *             만약 생략하면 '@ModelAttirube'가 자동 적용된다(단순 타입이 아니므로).
     *             그러면 QueryParameter가 존재하지 않음으로 helloData는
     *             username, age 모두 아무런 값도 없는 객체가 반환된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {

        log.info("username= {}, age= {}", data.getUsername(), data.getAge());

        return "ok";
    }

    /**
     * HttpEntity<HelloData> 통해서 requestBody의 json 형식 데이터를 HelloData 타입으로 받을 수 있음.
     * @param data 에는 HelloData 타입 객체가 들어있게 된다. 꺼내다 쓰면 된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * 받았던 json을 java 객체로 변환한 뒤, 다시 ResponseBody로 반환하면?
     * @param data HelloData 타입 객체로 자동 매핑된다.
     * @return java 객체가 다시 json 응답 자동 변환된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username= {}, age= {}", data.getUsername(), data.getAge());

        return data;
    }
}
