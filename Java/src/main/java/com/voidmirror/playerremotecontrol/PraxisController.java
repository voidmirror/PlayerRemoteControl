package com.voidmirror.playerremotecontrol;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PraxisController {

    PraxisGenerator praxisGenerator = new PraxisGenerator();

    @GetMapping("/praxis/get")
    public String getPraxis(@RequestParam String mode, @RequestParam int varNum) {
        praxisGenerator.setVarNum(varNum);
        switch (mode) {
            case "simple":
                return praxisGenerator.generateSimpleMedium(false);
            case "medium":
                return praxisGenerator.generateSimpleMedium(true);
            default:
                return "NaN";
        }
    }

}
