package com.doctor.visit.web.rest.vm.visit;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.SysUser;
import com.doctor.visit.service.VisitService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author kuanwang
 * @date 2020-04-02
 */
@RestController
@RequestMapping(Constants.API_BASE + "/visit")
public class VisitResource {

    private final VisitService visitService;

    public VisitResource(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("list")
    public ResponseEntity<List<SysUser>> list(HttpServletRequest request, Pageable pageable, String message) {
        return visitService.list(request, pageable, message);
    }
}
