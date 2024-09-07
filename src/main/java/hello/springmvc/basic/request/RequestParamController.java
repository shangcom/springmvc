package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username= {}, age= {}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // 컨트롤러 메서드가 String을 반환하면 viewReslover가 해당 viewName의 view를 찾음.
    // 따라서 컨트롤러 메서드에서 뷰가 아닌 문자를 바로 반환하고 싶으면 @ResponseBody 사용하면 된다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberId,
            @RequestParam("age") int age
    ) {
        log.info("username= {}, age= {}", memberId, age);

        return "ok";
    }

    /*
     * 쿼리 파라미터와 요청 파라미터의 이름이 같으면?
     * @RequestParam("username")에서 ("username") 생략 가능함.
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username= {}, age= {}", username, age);
        return "ok";
    }

    /*
    아예 @RequestParam 쓰지 않아도 단순 데이터 타입이며 쿼리 파라미터와 요청 파라미터 이름 같은 경우 자동으로 인식된다.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username= {}, age= {}", username, age);
        return "ok";
    }

    /**
     *
     * @param age 기본형 int를 사용하면 age값 없는 경우(null) 처리할 수 없기 때문에 Integer으로.
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username= {}, age= {}", username, age);
        return "ok";
    }

    /**
     *
     * @param age 아무런 값도 없어도 기본값으로 -1이 들어오니까 기본형(int)로 처리 가능
     *            브라우저의 개발자도구의 payload는 비어있으나, 서버에서 defaultValue를 넣어서 처리함.
     * 따라서 defaultValue를 입력해준 경우, required가 =ture이건 false이건 상관 없음.
     * 있으면 값이 들어올거고, 없어도 기본값이 들어가니까 그냥 required 자체를 안써도 괜찮음.
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username= {}, age= {}", username, age);
        return "ok";
    }

    /**
     *
     * @param paramMap Map 사용하면 요청 파라미터 전부 Map 하나로 받아올 수 있음
     *                 키는 마찬가지로 쿼리 파라미터 명을 사용하면 된다.
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {
        log.info("username= {}, age= {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

}
