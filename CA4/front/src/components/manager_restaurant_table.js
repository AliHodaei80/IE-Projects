import React from "react";
import AddRestaurantModal from "./add_restaurant_modal.js";
import { useState, useEffect } from "react";
import { fetchData } from "../utils/request_utils.js";
import "../styles/manager_restaurants.css";
import ManagerRestaurantsTableHeader from "./manager_restaurant_table_header.js";

let username = "ali";

function ManagerRestaurantsTable() {
  const [restaurants, setRestaurants] = useState([]);

  const handleFetchRestaurants = (response) => {
    console.log(response);
    if (response.success) {
      setRestaurants(response.data.restaurants);
    } else {
      console.log("error happend");
    }
  };

  const fetchRestaurants = () => {
    fetchData(
      "http://127.0.0.1:8080/user/" + username + "/restaurants",
      handleFetchRestaurants
    );
  };

  useEffect(() => {
    fetchRestaurants();
  }, []);
  return (
    <div className="table-responsive mx-auto w-100 mt-4 table-container p-4">
      <table className="table align-middle">
        <ManagerRestaurantsTableHeader />
        <tbody>
          {restaurants.map((restaurant) => (
            <tr key={restaurant.id}>
              <td className="restaurant-name">{restaurant.name}</td>
              <td className="restaurant-address text-center">
                {restaurant.address.city}, {restaurant.address.country}
              </td>
              <td className="text-end">
                <button className="manager-operation manage rounded-3 border-0">
                  Manage
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <AddRestaurantModal fetchRestaurants={fetchRestaurants} />
    </div>
  );
}

export default ManagerRestaurantsTable;
