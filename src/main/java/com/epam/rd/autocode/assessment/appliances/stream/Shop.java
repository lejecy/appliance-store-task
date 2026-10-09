package com.epam.rd.autocode.assessment.appliances.stream;

import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Order;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Shop implements Add, Find, Sort {

    private Set<Client> clients = new HashSet<>();
    private Set<Employee> employees = new HashSet<>();
    private Set<Order> orders = new HashSet<>();
    private Set<Appliance> appliances = new HashSet<>();
    private Set<Manufacturer> manufacturers = new HashSet<>();

    public Shop() {
    }

    public Shop(Set<Client> clients, Set<Employee> employees, Set<Order> orders,
            Set<Appliance> appliances, Set<Manufacturer> manufacturers) {
        this.clients = clients != null ? clients : new HashSet<>();
        this.employees = employees != null ? employees : new HashSet<>();
        this.orders = orders != null ? orders : new HashSet<>();
        this.appliances = appliances != null ? appliances : new HashSet<>();
        this.manufacturers = manufacturers != null ? manufacturers : new HashSet<>();
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(Set<Appliance> appliances) {
        this.appliances = appliances;
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    @Override
    public Manufacturer findManufacturerById(long id) {
        return manufacturers.stream()
                .filter(m -> m != null && m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Manufacturer with id=" + id + " was not found"));
    }

    @Override
    public Manufacturer findManufacturerByName(String name) {
        return manufacturers.stream()
                .filter(m -> m != null && Objects.equals(m.getName(), name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Manufacturer with name=" + name + " was not found"));
    }

    @Override
    public List<Order> findOrderByEmployee(Employee employee) {
        return orders.stream()
                .filter(order -> Objects.equals(order.getEmployee(), employee))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Order findCheapestOrder() {
        return orders.stream()
                .min(Comparator.comparing(this::calculateOrderTotal))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order findMostExpensiveOrder() {
        return orders.stream()
                .max(Comparator.comparing(this::calculateOrderTotal))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Manufacturer> sortManufacturersByName() {
        return manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getName, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Order> sortOrderByClientId() {
        return orders.stream()
                .sorted(Comparator.comparing(
                        order -> order != null && order.getClient() != null ? order.getClient().getId() : null,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Appliance> sortAppliancesByCategory() {
        return appliances.stream()
                .sorted(Comparator.comparing(Appliance::getCategory, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Order> sortOrderByAmount() {
        return orders.stream()
                .sorted(Comparator.comparing(this::calculateOrderTotal))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private BigDecimal calculateOrderTotal(Order order) {
        if (order == null || order.getAppliances() == null || order.getAppliances().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return order.getAppliances().values().stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}