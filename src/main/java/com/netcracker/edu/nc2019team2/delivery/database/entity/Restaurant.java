package com.netcracker.edu.nc2019team2.delivery.database.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Restaurant extends Entity{
    private boolean status;
    private String location;
    private Map<Item, BigDecimal> menu;
}
