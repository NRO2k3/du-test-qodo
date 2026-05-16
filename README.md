# du-test-qodo

Repo chia thành 2 phần trong cùng repository:

- `backend/` — Spring Boot MVC sample (intentionally vulnerable for PR-Agent review testing)
- `frontend/` — React vibe app đơn giản

## Backend

Project layers:

- `controller`: user, admin, payment, audit, and document entry points.
- `service`: business flows for login, profile updates, reports, payments, audit exports, and document sharing.
- `repository`: fake persistence methods and intentionally unsafe query construction.
- `model`: domain objects that intentionally expose sensitive fields.
- `dto`: request objects with intentionally missing validation.
- `config`: intentionally hard-coded secrets and environment settings.

Feature areas:

- User login, profile updates, password change/reset, search, bulk role update, deactivation, and avatar import.
- Admin dashboard, impersonation, feature flags, reports, and token rotation.
- Payment creation, refunds, saved payment methods, payment listing, and payment report export.
- Audit log recording, search, listing, and export.
- Document upload, download, search, bulk sharing, deletion, and document report export.

Intentional review cases include hard-coded secrets, IDOR, parameter tampering, SQL injection-style query building,
SSRF, excessive data exposure, sensitive logging, direct model binding, missing validation, controller business logic,
service-to-controller dependency, weak status naming, non-UUID IDs, missing audit ownership fields,
unsafe file path handling, unpaged document queries, and bulk sharing with query-in-loop behavior.

## Frontend

React + Vite app đơn giản:

- `src/App.jsx` — User list view với fetch từ backend API.
- `src/index.css` — Basic styling.

Chạy dev:
```bash
cd frontend
npm install
npm run dev
```

Build:
```bash
npm run build
```
