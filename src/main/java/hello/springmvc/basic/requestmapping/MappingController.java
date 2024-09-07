package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic")
//  @RequestMapping({"/hello-basic", "hello-go"}) //  배열 이용해서 복수 url 받을 수 있음.
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    @GetMapping(value = "mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable (경로 변수) 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userID -> @PathVariable String userId
     * 예시 : 요청이 /mapping/userA 로 온다면?
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId= {}", userId);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId= {}, orderId= {}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     * 요청 url에 쿼리 파라미터로 params에 지정한 "mode=debug" 쓰지 않으면 /mapping-param 호출 안됨.
     * /mapping-param?mode=debug
     * 이렇게 요청해야만 됨.
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * consumes = "application/json" 이렇게 적지 않고, Spring에서 정해둔
     * consumes = MediaType.APPLICATION_JSON_VALUE
     * 이 형식으로 써도 똑같다.
     * "text/html" 이것도 MediaType.TEXT_HTML_VALUE 이렇게 적을 수 있음.
     */
//    @PostMapping(value = "mapping-consume", consumes = "application/json")
    @PostMapping(value = "mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info(("mappingConsumes"));
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     * produces에 설정된 content-type과, 클라이언트의 Accept 헤더가 일치해야만 응답.
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
