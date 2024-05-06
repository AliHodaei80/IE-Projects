import React from "react";
import { useParams } from "react-router-dom";
import Header from "./components/header.js";
import Footer from "./components/footer.js";
import { useState, useEffect } from "react";
import RestaurantInfoNavBar from "./components/restaurant_info_nav.js";
import RestaurantReservationList from "./components/restaurant_reservation_list.js";
import RestaurantTableList from "./components/restuarant_table_list.js";
import { fetchData } from "./utils/request_utils.js";
import "./styles/manager_manage.css";

function ManageRestaurantPage() {
  const { id } = useParams();
  const [tableSelected, setTableSelected] = useState(-1);
  const [restaurant, setRestaurant] = useState({});
  const handleFetchRestaurantInfo = (response) => {
    console.log(response);
    if (response.success) {
      setRestaurant(response.data.restaurant);
    } else {
      console.log("error happend");
    }
  };

  const fetchRestaurantInfo = () => {
    fetchData(
      "/restaurants/" + id,
      null,
      handleFetchRestaurantInfo,
      (res) => {}
    );
  };

  useEffect(() => {
    fetchRestaurantInfo();
  }, []);

  document
    .getElementById("root")
    .classList.add("vh-100", "d-flex", "flex-column", "manage-restaurant-body");

  return (
    <>
      <div id="manage-restaurant-body" className="vh-100 d-flex flex-column">
        <Header />
        <RestaurantInfoNavBar
          address={"address" in restaurant ? restaurant.address : ""}
          restName={"name" in restaurant ? restaurant.name : ""}
        />
        <main className="flex-grow-1">
          <div className="container-fluid h-100">
            <div className="row h-100">
              <RestaurantReservationList
                restaurantId={id}
                tableSelected={tableSelected}
              />

              <RestaurantTableList
                restaurantId={id}
                tableSelected={tableSelected}
                setTableSelected={(tableNum) => {
                  setTableSelected(tableNum);
                  console.log("ManageRestaurantPage tableNum " + tableNum);
                  console.log("ManageRestaurantPage selected " + tableSelected);
                }}
              />
            </div>
          </div>
        </main>

        <Footer />
      </div>
    </>
  );
}

export default ManageRestaurantPage;
