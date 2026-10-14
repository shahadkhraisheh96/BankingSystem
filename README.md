# AeroBank Desktop Banking System

Enterprise Banking Application featuring a **Spring Boot 3.5.6** backend with JWT Security, Polymorphic Accounts, Strategy-pattern Withdrawal Limits, and a modern **Angular 20 Desktop** frontend.

---

## 🚀 Running the Project

### 1. Backend (Spring Boot :8080)
From the project root:
```powershell
.\run-backend.ps1
```
Or run `Main.java` directly in your IDE (IntelliJ IDEA / Antigravity IDE).

- **API Base URL**: `http://localhost:8080/api/`
- **H2 Database**: `jdbc:h2:~/project/bankingSystem`
- Initial test account created automatically: `admin@aerobank.com` / `admin123`

### 2. Frontend / Desktop App (:4200)
From the project root:
```powershell
.\run-desktop.ps1
```
Or inside `frontend/`:
```bash
npm start       # Launches Angular dev server at http://localhost:4200
npm run desktop # Opens native Electron desktop window
```

---

## 🏛️ System Features

1. **Enterprise Authentication**:
   - Secure login & registration using Spring Security and JWT.
   - Automatic HTTP request interceptor appending `Authorization: Bearer <token>`.
2. **Multi-Account Management**:
   - `CURRENT` Accounts (Withdrawal limit: $3,000)
   - `SAVING` Accounts (Withdrawal limit: $4,000)
   - `CORPORATE` Accounts (Withdrawal limit: $50,000)
3. **Transaction Engine**:
   - Instant Cash Deposits
   - Strategy-enforced Withdrawals
   - Inter-Account Wire Transfers
   - Audit Statements & Transaction Ledger
4. **Client Onboarding**:
   - Retail Individual Depositors
   - Corporate Business Entities