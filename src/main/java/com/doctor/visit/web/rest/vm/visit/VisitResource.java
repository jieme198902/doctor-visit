package com.doctor.visit.web.rest.vm.visit;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.service.VisitService;
import com.doctor.visit.web.rest.util.ComResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * @param busArticle
     * @param pageable
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = BusArticle.class)
    })
    @PostMapping("articleList")
    @ApiOperation(value = "文章列表")
    public Object articleList(BusArticle busArticle, Pageable pageable) {
        return visitService.articleList(busArticle, pageable);
    }

}
