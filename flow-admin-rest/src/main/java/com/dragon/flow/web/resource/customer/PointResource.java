package com.dragon.flow.web.resource.customer;

import com.dragon.flow.model.customer.Point;
import com.dragon.flow.web.resource.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow/customer/point")
public class PointResource extends BaseResource<Point> {

}
