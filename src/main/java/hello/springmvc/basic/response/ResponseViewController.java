package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "hello!");
        /* response/hello : 상대경로. 앞에 '/' 붙였으면 절대경로. 뷰리졸버는 상대 경로로 뷰를 찾으므로 붙이지 않아도 같은 결과임. */
        return mav;
    }

    /**
     * @param model Spring에서 제공하는 Model.
     * @return String을 반환하는 경우, 해당 String을 이름으로 가진 view를 찾음.
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    /**
     *  **** 알아만 두고 사용하지는 말자. ***
     * void를 반환하는 경우
     * '@Controller'를 사용하고, HttpServltResponse 같은 Http 멧지 바디를 처리하는 파라미터가 없으면
     * 요청 URL을 참고해서 논리 뷰 이름으로 사용함.
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }

}
