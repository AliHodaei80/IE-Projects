import React from "react";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
import UserMailInfo from "./components/user_mail_info.js";
import CustomerReservations from "./components/customer_reservations_table.js";
import "./styles/customer.css";
export default function ReservationsPage() {
  return (
    <div>
      <Header />
      <main className="flex-grow-1">
        <div className="p-3 container">
          <UserMailInfo />
          <CustomerReservations />
        </div>
      </main>

      <Footer />
    </div>
  );
}
