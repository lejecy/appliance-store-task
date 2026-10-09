package com.epam.rd.autocode.assessment.appliances.stream;

import com.epam.rd.autocode.assessment.appliances.model.Appliance;
import com.epam.rd.autocode.assessment.appliances.model.Client;
import com.epam.rd.autocode.assessment.appliances.model.Employee;
import com.epam.rd.autocode.assessment.appliances.model.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.model.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public Set<Client> getClients() { return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }
    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }
    public Set<Order> getOrders() { return orders; }
    public void setOrders(Set<Order> orders) { this.orders = orders; }
    public Set<Appliance> getAppliances() { return appliances; }
    public void setAppliances(Set<Appliance> appliances) { this.appliances = appliances; }
    public Set<Manufacturer> getManufacturers() { return manufacturers; }
    public void setManufacturers(Set<Manufacturer> manufacturers) { this.manufacturers = manufacturers; }

    @Override
    public void addClient(Client client) { clients.add(client); }
    @Override
    public void addEmployee(Employee employee) { employees.add(employee); }
    @Override
    public void addAppliance(Appliance appliance) { appliances.add(appliance); }
    @Override
    public void addOrder(Order order) { orders.add(order); }
    @Override
    public void addManufacturer(Manufacturer manufacturer) { manufacturers.add(manufacturer); }

    @Override
    public Manufacturer findManufacturerById(long id) {
        for (Manufacturer m : manufacturers) {
            if (m.getId() == id) return m;
        }
        throw new RuntimeException("Manufacturer with id=" + id + " was not found");
    }

    @Override
    public Manufacturer findManufacturerByName(String name) {
        for (Manufacturer m : manufacturers) {
            if (Objects.equals(m.getName(), name)) return m;
        }
        throw new RuntimeException("Manufacturer with name=" + name + " was not found");
    }

    @Override
    public List<Order> findOrderByEmployee(Employee employee) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (Objects.equals(o.getEmployee(), employee)) {
                result.add(o);
            }
        }
        return result;
    }

    @Override
    public Order findCheapestOrder() {
        if (orders.isEmpty()) throw new RuntimeException("Order not found");
        Order cheapest = null;
        BigDecimal min = null;
        for (Order o : orders) {
            BigDecimal total = calculateOrderTotal(o);
            if (min == null || total.compareTo(min) < 0) {
                min = total;
                cheapest = o;
            }
        }
        return cheapest;
    }

    @Override
    public Order findMostExpensiveOrder() {
        if (orders.isEmpty()) throw new RuntimeException("Order not found");
        Order expensive = null;
        BigDecimal max = null;
        for (Order o : orders) {
            BigDecimal total = calculateOrderTotal(o);
            if (max == null || total.compareTo(max) > 0) {
                max = total;
                expensive = o;
            }
        }
        return expensive;
    }

    @Override
    public List<Manufacturer> sortManufacturersByName() {
        List<Manufacturer> list = new LinkedList<>(manufacturers);
        list.sort((m1, m2) -> {
            if (m1.getName() == null && m2.getName() == null) return 0;
            if (m1.getName() == null) return 1;
            if (m2.getName() == null) return -1;
            return m1.getName().compareTo(m2.getName());
        });
        return list;
    }

    @Override
    public List<Order> sortOrderByClientId() {
        List<Order> list = new LinkedList<>(orders);
        list.sort((o1, o2) -> {
            Long id1 = (o1 != null && o1.getClient() != null) ? o1.getClient().getId() : null;
            Long id2 = (o2 != null && o2.getClient() != null) ? o2.getClient().getId() : null;
            if (id1 == null && id2 == null) return 0;
            if (id1 == null) return 1;
            if (id2 == null) return -1;
            return id1.compareTo(id2);
        });
        return list;
    }

    @Override
    public List<Appliance> sortAppliancesByCategory() {
        List<Appliance> list = new LinkedList<>(appliances);
        list.sort((a1, a2) -> {
            if (a1.getCategory() == null && a2.getCategory() == null) return 0;
            if (a1.getCategory() == null) return 1;
            if (a2.getCategory() == null) return -1;
            return a1.getCategory().compareTo(a2.getCategory());
        });
        return list;
    }

    @Override
    public List<Order> sortOrderByAmount() {
        List<Order> list = new LinkedList<>(orders);
        list.sort((o1, o2) -> calculateOrderTotal(o1).compareTo(calculateOrderTotal(o2)));
        return list;
    }

    private BigDecimal calculateOrderTotal(Order order) {
        if (order == null || order.getAppliances() == null || order.getAppliances().isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal price : order.getAppliances().values()) {
            if (price != null) {
                total = total.add(price);
            }
        }
        return total;
    }
}