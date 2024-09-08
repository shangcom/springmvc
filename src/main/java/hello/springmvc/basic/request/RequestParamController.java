package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    명시적으로 작성하면 코드의 가독성이 향상되고, 해당 메서드가 객체 바인딩을 하는 역할임을 더욱 명확히 알 수 있음.
    웬만하면 그냥 쓰자.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username= {}, age= {}", username, age);
        return "ok";
    }

    /**
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
     * @param age 아무런 값도 없어도 기본값으로 -1이 들어오니까 기본형(int)로 처리 가능
     *            브라우저의 개발자도구의 payload는 비어있으나, 서버에서 defaultValue를 넣어서 처리함.
     *            따라서 defaultValue를 입력해준 경우, required가 =ture이건 false이건 상관 없음.
     *            있으면 값이 들어올거고, 없어도 기본값이 들어가니까 그냥 required 자체를 안써도 괜찮음.
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

/*    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData= {}", helloData); //toString으로 자동 변환됨.

        return "ok";
    }*/

    /**
     * 위 메서드를 @ModelAttribute 사용하는 형태로 변경.
     * 데이터 바인딩: 요청 파라미터를 자동으로 객체에 매핑합
     * 모델 전달: 객체를 뷰에 전달할 때 사용됩
     * 코드 간결화: 여러 필드를 일일이 매핑할 필요 없이 자동으로 바인딩
     * @param helloData 모델로 사용할 클래스를 미리 생성해둬야함.
     *                  HelloData 클래스에 @Data(롬복) 적용해서 setter 등 메서드 자동으로 생성했음.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("HelloData= {}", helloData);
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * '@RequestParam'과 마찬가지로, 객체의 필드 이름과 요청 파라미터 이름이 일치하는 경우,
     * '@ModelAttribute' 또한 생략이 가능하다.
     * 어노테이션을 생략했을 경우, RequestParam은 단순 데이터 타입에, 나머지는 ModelAttribute로 처리된다.
     * 그래도 명확성 위해 써주는 것이 좋다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("HelloData= {}", helloData);
        log.info("username= {}, age= {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
}
