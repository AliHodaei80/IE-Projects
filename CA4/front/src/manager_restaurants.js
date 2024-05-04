import React from "react";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
import UserMailInfo from "./components/user_mail_info.js";
import ManagerRestaurantsTable from "./components/manager_restaurant_table.js";

// import "./styles/normalize.css";
// import "./styles/bootstrطap.min.css";
// import "./styles/header.css";
// import "./styles/font.css";
// import "./styles/footer.css";

export default function ManagerRestaurants() {
  return (
    <div>
      <Header />
      <main className="flex-grow-1">
        <div className="p-3 container">
          <UserMailInfo />
          <ManagerRestaurantsTable />
        </div>
      </main>

      <Footer />
    </div>
  );
}
