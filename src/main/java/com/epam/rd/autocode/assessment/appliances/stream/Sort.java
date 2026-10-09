package com.epam.rd.autocode.assessment.appliances.stream;

import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Order;

import java.util.List;

public interface Sort {
    List<Manufacturer> sortManufacturersByName();

    List<Order> sortOrderByClientId();

    List<Appliance> sortAppliancesByCategory();

    List<Order> sortOrderByAmount();
}
