
package com.app.multistat.controller;

import com.app.multistat.entity.IstoricActivitateUtilizator;
import com.app.multistat.service.IstoricActivitateUtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/istoric")
public class IstoricActivitateUtilizatorController {

    @Autowired
    private IstoricActivitateUtilizatorService istoricService;

    @GetMapping
    public ResponseEntity<List<IstoricActivitateUtilizator>> getIstoric() {
        List<IstoricActivitateUtilizator> istoric = istoricService.obtineIstoric();
        return ResponseEntity.ok(istoric);
    }
}