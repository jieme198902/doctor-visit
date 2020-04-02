package com.doctor.visit.web.rest.vm.visit;

import com.doctor.visit.config.Constants;
import com.google.common.collect.Maps;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@RequestMapping(Constants.API_BASE + "/visit")
public class VisitResource {


    @PostMapping("list")
    public ResponseEntity<Map<String, Object>> list(HttpServletRequest request, Pageable pageable, String message) {
        Map<String, Object> data = Maps.newHashMap();
        data.put(message, message);

        return ResponseEntity.ok(data);
    }
}
