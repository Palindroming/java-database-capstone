# MySQL Database Design

## Table: patients
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `name`: VARCHAR(100), NOT NULL  
- `email`: VARCHAR(100), UNIQUE, NOT NULL  
- `phone`: VARCHAR(20), NOT NULL  
- `date_of_birth`: DATE  
- `gender`: VARCHAR(10)  
- `created_at`: DATETIME, DEFAULT CURRENT_TIMESTAMP  

> Email format should be validated in application logic.  
> If a patient is deleted, dependent appointments should be handled with `ON DELETE CASCADE` or `SET NULL` depending on policy.

---

## Table: doctors
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `name`: VARCHAR(100), NOT NULL  
- `specialty`: VARCHAR(100)  
- `email`: VARCHAR(100), UNIQUE, NOT NULL  
- `phone`: VARCHAR(20), NOT NULL  
- `created_at`: DATETIME, DEFAULT CURRENT_TIMESTAMP  

> Overlapping appointments should be prevented in application logic.

---

## Table: appointments
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `doctor_id`: INT, Foreign Key → doctors(id) ON DELETE CASCADE  
- `patient_id`: INT, Foreign Key → patients(id) ON DELETE CASCADE  
- `appointment_time`: DATETIME, NOT NULL  
- `status`: INT, NOT NULL  `-- 0 = Scheduled, 1 = Completed, 2 = Cancelled`  
- `notes`: TEXT  

> Appointment history should be retained even after status changes.

---

## Table: admin
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `username`: VARCHAR(50), UNIQUE, NOT NULL  
- `password_hash`: VARCHAR(255), NOT NULL  
- `role`: VARCHAR(20), NOT NULL  `-- e.g., superadmin, receptionist`  
- `created_at`: DATETIME, DEFAULT CURRENT_TIMESTAMP  

---

## Table: clinic_locations
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `name`: VARCHAR(100), NOT NULL  
- `address`: VARCHAR(255), NOT NULL  
- `phone`: VARCHAR(20)  
- `email`: VARCHAR(100)  
- `open_hours`: VARCHAR(100)  

> Used to support multiple branch clinics.

---

## Table: payments
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `appointment_id`: INT, Foreign Key → appointments(id) ON DELETE SET NULL  
- `amount`: DECIMAL(10, 2), NOT NULL  
- `payment_method`: VARCHAR(50)  `-- e.g., cash, credit card, insurance`  
- `payment_date`: DATETIME, DEFAULT CURRENT_TIMESTAMP  
- `status`: VARCHAR(20)  `-- e.g., paid, pending, failed`  

---

## Table: prescriptions (Optional)
- `id`: INT, Primary Key, AUTO_INCREMENT  
- `appointment_id`: INT, Foreign Key → appointments(id), NOT NULL  
- `medicine_name`: VARCHAR(100), NOT NULL  
- `dosage`: VARCHAR(50), NOT NULL  
- `instructions`: TEXT  

> Tied directly to an appointment for medical traceability.


# MongoDB Collection Design

### Collection: messages

```json
{
  "_id": "ObjectId('665f123abc890')",
  "appointmentId": 82,
  "patientId": 204,
  "doctorId": 17,
  "messages": [
    {
      "sender": "patient",
      "timestamp": "2025-06-04T09:30:00Z",
      "text": "Hi doctor, should I continue taking the antibiotics?",
      "attachments": []
    },
    {
      "sender": "doctor",
      "timestamp": "2025-06-04T10:00:00Z",
      "text": "Yes, please continue for 5 more days.",
      "attachments": [
        {
          "fileName": "prescription_update.pdf",
          "url": "https://clinic.com/files/prescriptions/82.pdf",
          "uploadedAt": "2025-06-04T10:00:01Z"
        }
      ]
    }
  ],
  "tags": ["follow-up", "prescription"],
  "createdAt": "2025-06-04T09:30:00Z",
  "updatedAt": "2025-06-04T10:00:01Z"
}

