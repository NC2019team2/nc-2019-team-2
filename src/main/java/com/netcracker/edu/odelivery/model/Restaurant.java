package com.netcracker.edu.odelivery.model;

import java.math.BigDecimal;
import java.util.Map;

public class Restaurant extends Entity{
    private Boolean status;
    private String location;
    private Map<Item, BigDecimal> menu;
}
