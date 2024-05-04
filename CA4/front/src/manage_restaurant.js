import React from "react";
import { useParams } from "react-router-dom";
import Header from "./components/header.js";
import Footer from "./components/footer.js";

import RestaurantInfoNavBar from "./components/restaurant_info_nav.js";
import RestaurantReservationList from "./components/restaurant_reservation_list.js";
import RestaurantTableList from "./components/restuarant_table_list.js";
import "./styles/manager_manage.css";

let restaurant = {
  id: 1,
  name: "The Commoner",
  avgAmbianceScore: 0.0,
  avgOverallScore: 0.0,
  avgServiceScore: 0.0,
  avgFoodScore: 0.0,
  startTime: "07:00:00",
  endTime: "23:00:00",
  type: "American",
  description:
    "At our gastropub, we don't distinguish between commoners and kings; we just want to feed the good people of Pittsburgh. In the restaurant, seasonal menus add a modern flair to classic comforts, complemented by a robust selection of local beers and craft spirits. It's all served in an industrial-inspired setting in downtown Pittsburgh. Come and join us for an uncommonly good time.",
  managerUsername: "ali",
  address: {
    city: "Pittsburgh",
    country: "US",
    street: "620 William Penn Place",
  },
};

function ManageRestaurantPage() {
  const { id } = useParams();

  // Fetch restaurant details based on id
  //   document.body.classList.add("vh-100", "d-flex", "flex-column");
  document
    .getElementById("root")
    .classList.add("vh-100", "d-flex", "flex-column", "manage-restaurant-body");

  return (
    <>
      <Header />
      {/*  */}
      <RestaurantInfoNavBar
        address={restaurant.address}
        restName={restaurant.name}
      />
      {/*  */}
      <main className="flex-grow-1">
        <div className="container-fluid h-100">
          <div className="row h-100">
            {/*  */}
            <RestaurantReservationList restaurantId={id} />
            {/*  */}
            {/*  */}
            <RestaurantTableList restaurantId={id} />
            {/*  */}
          </div>
        </div>
      </main>

      <Footer />
    </>
  );
}

export default ManageRestaurantPage;
