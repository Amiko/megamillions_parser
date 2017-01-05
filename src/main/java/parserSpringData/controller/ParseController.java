package parserSpringData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parserSpringData.entity.DrawResult;
import parserSpringData.service.DrawResultService;

import java.io.IOException;

/**
 * Created by amiko on 28-Dec-16.
 */

@RestController
@RequestMapping("/springData")
public class ParseController {

    @Autowired
    DrawResultService drawResultService;

    @GetMapping
    public void getDrawResults() throws IOException {
        drawResultService.getDrawResult();
    }
}
