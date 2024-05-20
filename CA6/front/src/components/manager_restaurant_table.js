import React from "react";
import AddRestaurantModal from "./add_restaurant_modal.js";
import { useState, useEffect } from "react";
import { fetchData } from "../utils/request_utils.js";
import { Link } from "react-router-dom";
import "../styles/manager_restaurants.css";
import ManagerRestaurantsTableHeader from "./manager_restaurant_table_header.js";
import { useAuth } from "../context/AuthContext";

function ManagerRestaurantsTable() {
  const { authDetails } = useAuth();

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
      "/user/" + authDetails.user.username + "/restaurants",
      null,
      handleFetchRestaurants,
      (res) => {}
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
                <Link to={`/manager_restaurants/${restaurant.id}`}>
                  <button className="manager-operation manage rounded-3 border-0">
                    Manage
                  </button>
                </Link>
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
