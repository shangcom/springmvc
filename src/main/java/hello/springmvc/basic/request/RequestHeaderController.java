package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RequestHeaderController.class);
    //위의 @Slf4j가 대신함.

    /**
     * @param request
     * @param response
     * @param httpMethod 어떤 종류의 HTTP 메서드인가?
     *                   ex) HttpMethod.GET
     * @param locale 국가, 언어
     * @param headerMap HTTP 헤더 정보 받기 위해, 단일 키 - 복수 밸류 지원하는 MultiValueMap 사용.
     *                  여기서는 <String, String>이라고 썼지만, 제대로 하려면 <String, List<String>>이 옳다.
     *                  <String, String>으로 하면, value가 복수일 경우, spring이 알아서 List<String>으로 처리해줌.
     *                  따라서 복수 value가 들어갈 수 있음을 명시적으로 보여주기 위해서는 기왕이면 List 사용하는 것이 좋음.
     * @param host 클라이언트가 보낸 Host 헤더. 클라이언트가 요청하는 서버의 도메인 이름과 포트 번호 포함.
     *             ex) localhost:8080
     * @param cookie HTTP 쿠키의 값을 컨트롤러 메서드의 파라미터로 바인딩.
     *               여기서는 클라이언트의 요청에서 myCookie라는 이름의 쿠키 값을 가리킴.
     *               required = false : 오류 방지. 쿠키가 존재하지 않으면, 파라미터 값 null.
     */
    @RequestMapping("/headers")
    public String headers(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod httpMethod,
            Locale locale,
            @RequestHeader MultiValueMap<String, String> headerMap,
            @RequestHeader("host") String host,
            @CookieValue(value = "myCookie", required = false) String cookie
    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}
