package com.netcracker.edu.nc2019team2.delivery.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends Entity{
    private Boolean status;
    private String location;
    private Map<Item, BigDecimal> menu;
}
