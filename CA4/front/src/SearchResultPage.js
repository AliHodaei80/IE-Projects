import "react-toastify/dist/ReactToastify.css";
import "./styles/search_result.css";
import React, { useState, useEffect } from "react";
import Header from "./components/header.js";
import RestaurantCard from "./components/restaurant_card.js";
import "./styles/header.css";
import "./styles/search_result.css";
import "./styles/footer.css";

import { useLocation } from "react-router-dom";

export default function SearchResultPage() {
  const { state } = useLocation();
  return (
    <main class="flex-grow-1">
      <Header></Header>
      <div class="p-3 container">
        <div class="search-result-title">Search Results</div>
        <div class="restaurants row p-2 row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 row-cols-xxl-6 g-4">
          {state && state.restaurants && state.restaurants.length > 0
            ? state.restaurants.map((restaurant, index) => (
                <RestaurantCard key={index} data={restaurant} />
              ))
            : null}
        </div>
      </div>
    </main>
  );
}
