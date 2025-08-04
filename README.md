# Portfolio Management System

This project is a portfolio investment calculator built with **Java 17**, **Spring Boot**, **H2 database**, and **Apache POI**. 
It models the evolution of financial portfolios over time using real asset price and weight data from Excel files.

---

## Problem Definition

Given:

- A set of **assets**
- Two **portfolios** with initial weights at time `t = 0`
- Historical **price data** for each asset

We must:

1. Load and persist this data from an Excel file.
2. Model the relationships between assets, portfolios, prices, and weights.
3. Calculate:
   - Initial **quantities** of each asset
   - Daily **portfolio value**
   - Daily **asset weights**
4. Simulate **rebalancing**
5. (Bonus) Process **buy/sell trades**
6. (Bonus) Expose an API for **visualization** (optional for now).

---

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- H2 in-memory database (preconfigured for testing)
- Apache POI (for Excel processing)
- Swagger/OpenAPI UI
- Maven

---

## Project Structure

```
portafolio-inversion/
├── src/
│ ├── main/
│ │ ├── java/ 
│ │ │ └── com.jp.orpha.portafolio_inversion
│ │ │ ├── controller/ 
│ │ │ │ ├── etl
│ │ │ ├── dto/ 
│ │ │ ├── entities/ 
│ │ │ ├── enums/ 
│ │ │ ├── exceptions/ 
│ │ │ ├── mapper/ 
│ │ │ ├── repository/ 
│ │ │ └── service/ 
│ │ └── resources/
│ │ ├── application.properties
│ │ └── static/
│ │ └── index.html 
└── README.md

```

---

## How to Run

1. Clone the repository
2. Import into your favorite IDE (IntelliJ, Eclipse, etc.)
3. Run the `PortfolioApplication.java`
4. Access Swagger UI at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
5. Upload the provided Excel file via Swagger to load data

---

## Test Excel File

The project uses an Excel file (`datos.xlsx`) with:

- A **Weights** sheet: containing asset weights per portfolio on `15/02/2022`
- A **Prices** sheet: historical prices for each asset (one column per asset)

---

## API Endpoints & Their Purpose

| Feature                 | Endpoint                                                                                                       | Description                                                                       |
| ----------------------- | -------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| **Upload Excel File**   | `POST /api/v1/etl/upload`                                                                                      | Uploads the `datos.xlsx` file and stores data into H2                             |
| **Initial Holdings**    | `GET /api/v1/portfolio/calculations/{portfolioId}/initial-quantities`                                          | Calculates initial quantities (`c_{i,0}`) using weights and prices                |
| **Portfolio Status**    | `GET /api/v1/portfolio/calculations/{portfolioId}/portfolio-status`                                            | Returns the latest value and weights of the portfolio                             |
| **Weight Calculation**  | `GET /api/v1/portfolio/calculations/{portfolioId}/calculate-weights`                                           | Calculates current asset weights in the portfolio                                 |
| **Portfolio Evolution** | `GET /api/v1/portfolio-calculations/{portfolioId}/portfolio-evolution?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | Returns daily portfolio value and weights over time                               |
| **Portfolio Rebalance** | `GET /api/v1/portfolio-calculations/{portfolioId}/rebalance`                                                   | Suggests trades (buy/sell) to rebalance the portfolio according to target weights |
| **Process Trade**       | `POST /api/v1/transactions/process`                                                                            | Applies a buy/sell trade to a given portfolio                                     |

---

## Quick Demo for Bonus 1
A lightweight HTML view was added to visualize the portfolio rebalancing quickly without installing frameworks like React.

Open this URL after run the backend and loading the Excel file:
http://localhost:8080/index.html

There you will be able to:
- Enter the ID of a portfolio.
- View table with proposed rebalancing (current value, target, difference, share, etc).
- 
---

## Key Concepts

- **Portfolio Value** `V_t`:\
  \(V_t = \sum_{i=1}^N (p_{i,t} \cdot c_{i,t})\)

- **Weight per Asset** `w_{i,t}`:\
  \(w_{i,t} = \frac{p_{i,t} \cdot c_{i,t}}{V_t}\)

- **Initial Quantity** `c_{i,0}`:\
  \(c_{i,0} = \frac{w_{i,0} \cdot V_0}{p_{i,0}}\)

- **Trade Processing**:\
  Allows selling or buying a given amount of an asset and updates holdings accordingly.

---

## Notes

- The project uses **H2 in-memory DB**. You can view the data at:

  ```
  http://localhost:8080/h2-console
  ```

  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave blank)

- The data is stored **after uploading the Excel file**.

- Swagger UI provides a complete way to test every feature.


---

## Author

Made by **JP Orphanopoulos**\
(Software engineer, music producer & mystery novelist in progress)

