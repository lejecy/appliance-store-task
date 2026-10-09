# Appliances Shop (Stream API Assessment)

A Java 17 enterprise-style domain model and business service implementing filtering, sorting, and aggregate calculation algorithms using the **Java Stream API** and strict OOP design principles.

---

## 🛠 Tech Stack

* **Language:** Java 17
* **Build Tool:** Apache Maven
* **Testing:** JUnit 5 (Jupiter Engine & Params)
* **Code Analysis & Verification:** Spoon AST Core (Static Analysis)

---

## 📌 Project Architecture & Domain Model

The project models an appliance store management system with complete entity encapsulation, inheritance hierarchies, custom string format conventions, and object identity contracts (`equals`, `hashCode`, `toString`).

### Core Entities:
* `User` — Base entity containing core user credentials (`id`, `name`, `email`, `password`).
* `Client` — Inherits `User`, extends with payment card data (`card`).
* `Employee` — Inherits `User`, extends with department management (`department`).
* `Manufacturer` — Producer company definition (`id`, `name`).
* `Appliance` — Catalog entity with category, electrical power characteristics, and manufacturing relationships.
* `Order` — Purchase transaction tracking customer, processing employee, and a price mapping `Map<Appliance, BigDecimal>`.

---

## 🚀 Business Services & Functional Features (`Shop.java`)

The central business service implements three core contracts: `Add`, `Find`, and `Sort`.

### 1. Data Modification (`Add`)
* In-memory set storage ensuring uniqueness for clients, employees, appliances, orders, and manufacturers.

### 2. Search & Retrieval (`Find`)
* **`findManufacturerById(long id)`** — Stream filter lookup with strict descriptive exceptions.
* **`findManufacturerByName(String name)`** — Case-sensitive name resolution.
* **`findOrderByEmployee(Employee employee)`** — Order filtering supporting null-safe employee assignment.
* **`findCheapestOrder()` & `findMostExpensiveOrder()`** — Dynamic order cost calculation summing internal `BigDecimal` appliance maps with min/max stream collectors.

### 3. Natural & Comparator Sorting (`Sort`)
* **`sortManufacturersByName()`** — Natural string ordering with null values relegated to the end of the collection.
* **`sortOrderByClientId()`** — Orders sorted by client unique identifiers.
* **`sortAppliancesByCategory()`** — Appliance classification sorting.
* **`sortOrderByAmount()`** — Orders sorted ascending by total monetary sum.
* All sorted results are collected into `LinkedList` sequences to satisfy strict interface contracts.

---

## 🧪 Testing & Verification

The project is backed by a test suite verifying structural AST conformance, field access modifiers, and business logic execution:

* **Total Tests:** 185
* **Failures:** 0
* **Errors:** 0
* **Execution Time:** ~4.6 seconds

```bash
mvn clean test
[INFO] Results:
[INFO] Tests run: 185, Failures: 0, Errors: 0, Skipped: 0
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
